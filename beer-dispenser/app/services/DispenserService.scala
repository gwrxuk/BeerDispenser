package services

import java.util.UUID
import javax.inject.Inject
import models.{APIError, Dispenser}
import repository.DispenserRepositoryImpl

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
class DispenserService @Inject()(dispenserRepositoryImpl: DispenserRepositoryImpl){
  def getDispensers(): Future[List[Dispenser]] = {
    dispenserRepositoryImpl.getDispensers()
  }

  def getDispenserById(id: UUID): Future[Option[Dispenser]] = {
    dispenserRepositoryImpl.getDispenserById(id)
  }

  def createDispenser(dispenser: Dispenser): Future[Option[APIError]] = {
    dispenserRepositoryImpl.createDispenser(dispenser)
  }
  def updateDispenser(dispenser: Dispenser): Future[Int] = {
    dispenserRepositoryImpl.updateDispenser(dispenser)
  }

}
