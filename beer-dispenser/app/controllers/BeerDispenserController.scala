package controllers

import models.{APIError, Beer, Dispenser, NewDispenser, Report}
import org.joda.time.DateTime
import play.api.libs.json.Format.GenericFormat
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Singleton
import javax.inject.Inject
import scala.collection.mutable
import play.api.libs.json._
import services.{BeerService, DispenserService}

import java.time.Instant
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class BeerDispenserController @Inject()(val controllerComponents: ControllerComponents, dispenserService: DispenserService, beerService: BeerService) extends BaseController {
  private val beerList = new mutable.ListBuffer[Beer]()
  private val dispenserList = new mutable.ListBuffer[Dispenser]()
  /** Adding a new dispenser
   *
   * @return The total number of seconds elapsed for this timer.
   */
  def addNewDispenser(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val newDispenser: Option[NewDispenser] =
      jsonObject.flatMap(
        Json.fromJson[NewDispenser](_).asOpt
      )
    newDispenser match {
      case Some(newItem) =>
        try {
          val dispenser = Dispenser(UUID.randomUUID(), newItem.flow_volume, is_open = false, Instant.now.getEpochSecond, newItem.brand, newItem.price)
          dispenserService.createDispenser(dispenser)
          Created(Json.toJson(dispenser))
        } catch {
          case e: Any => println(e)
            BadRequest
        }
      case None =>
        BadRequest
    }
  }

  def openDispenser(dispenserId: UUID): Action[AnyContent] = Action.async {
    dispenserService.getDispenserById(dispenserId).map {
        case Some(dispenser) =>
          if (dispenser.is_open) {
            BadRequest(Json.toJson(APIError("Dispenser has been opened")))
          } else {
            val updateDispenser = dispenser.copy(is_open = true, updated_at = Instant.now.getEpochSecond)
            dispenserService.updateDispenser(updateDispenser)
            Ok(Json.toJson(updateDispenser))
          }
        case None => NotFound
    }
  }

  def closeDispenser(dispenserId: UUID): Action[AnyContent] = Action.async {
    dispenserService.getDispenserById(dispenserId).map {
      case Some(dispenser) =>
        if (!dispenser.is_open) {
          BadRequest(Json.toJson(APIError("Dispenser has been closed")))
        } else {
          val updateDispenser = dispenser.copy(is_open = false, updated_at = Instant.now.getEpochSecond)
          dispenserService.updateDispenser(updateDispenser)
          val spentTime = updateDispenser.updated_at - dispenser.updated_at
          val beer = Beer(UUID.randomUUID(), spentTime, dispenserId, spentTime * dispenser.flow_volume, new DateTime().toString("yyyyMMdd"))
          beerService.createBeer(beer)
          Ok(Json.toJson(updateDispenser))
        }
      case None => NotFound
    }
  }

  def getDispenserReport: Action[AnyContent] = Action {
    if (beerList.isEmpty) {
      NoContent
    } else {
      val report = mutable.Map[UUID, Report]()

      beerList.foreach(beer =>
        if (report.contains(beer.dispenserId)) {
          val item: Option[Report] = report.get(beer.dispenserId)
          val newItem: Report = item.get.copy(times = item.get.times + 1, duration = item.get.duration + beer.duration, income = item.get.income + beer.total)
          report.update(beer.dispenserId, newItem)
        } else {
          val newItem: Report = Report(dispenserId = beer.dispenserId, times = 1, duration = beer.duration, income = beer.total)
          report.put(beer.dispenserId, newItem)
        }
      )
      Ok(Json.toJson(report.toMap))
    }
  }
}
