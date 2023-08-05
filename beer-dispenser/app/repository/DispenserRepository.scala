package repository

import models.{APIError, Dispenser}

import java.util.UUID
import scala.concurrent.Future

trait DispenserRepository {
  def getDispensers(): Future[List[Dispenser]]
  def getDispenserById(id: UUID): Future[Option[Dispenser]]
  def createDispenser(dispenser: Dispenser): Future[Option[APIError]]
  def deleteDispenserById(id: Int): Future[Int]
  def updateDispenser(dispenser: Dispenser): Future[Int]
}