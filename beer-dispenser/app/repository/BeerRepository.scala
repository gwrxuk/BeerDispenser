package repository

import models.{APIError, Beer}

import java.util.UUID
import scala.concurrent.Future

trait BeerRepository {
  def createBeer(beer: Beer): Future[Option[APIError]]
}