package repository


import java.util.UUID
import javax.inject.Inject
import play.api.db.Database
import models.{APIError, Dispenser}

import scala.concurrent.{ExecutionContext, Future}

class DispenserRepositoryImpl @Inject()(db: Database,
                                        databaseExecutionContext: ExecutionContext,
                                        dispenserRepositoryDAO: DispenserRepositoryDAO) {

  def getDispensers(): Future[List[Dispenser]] = {
    Future {
      val dispenserList = db.withConnection { implicit conn =>
        dispenserRepositoryDAO.get()
      }
     dispenserList
    }(databaseExecutionContext)
  }

  def updateDispenser(dispenser: Dispenser):  Future[Int] = {
    Future {
      db.withConnection { implicit conn =>
        dispenserRepositoryDAO.update(dispenser)
      }
    }(databaseExecutionContext)
  }

  def createDispenser(dispenser: Dispenser): Future[Option[APIError]] = {
    Future {
      db.withConnection { implicit conn =>
        dispenserRepositoryDAO.insert(dispenser)
      }
    }(databaseExecutionContext)
  }

  def getDispenserById(id: UUID): Future[Option[Dispenser]] = {
    Future {
      val dispenserList = db.withConnection { implicit conn =>
        dispenserRepositoryDAO.getById(id)
      }
      dispenserList
    }(databaseExecutionContext)
  }
}
