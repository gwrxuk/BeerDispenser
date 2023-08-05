package models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class Beer(id: UUID, duration: Long, dispenserId: UUID, total: Double, day: String)
object Beer {
  implicit val beerFormat: OFormat[Beer] = Json.format[Beer]
}