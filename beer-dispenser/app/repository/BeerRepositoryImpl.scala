package repository

import models.{APIError, Beer, Dispenser}
import play.api.db.Database

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class BeerRepositoryImpl @Inject()(db: Database,
                                   databaseExecutionContext: ExecutionContext,
                                   beerRepositoryDAO: BeerRepositoryDAO) {

  def createBeer(beer: Beer): Future[Option[APIError]] = {
    Future {
      db.withConnection { implicit conn =>
        beerRepositoryDAO.insert(beer)
      }
    }(databaseExecutionContext)
  }
}
