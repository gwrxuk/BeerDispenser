package services

import models.{APIError, Beer, Dispenser}
import repository.{BeerRepositoryImpl, DispenserRepositoryImpl}

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future

class BeerService @Inject()(beerRepositoryImpl: BeerRepositoryImpl) {
  def createBeer(beer: Beer): Future[Option[APIError]] = {
    beerRepositoryImpl.createBeer(beer)
  }
}
