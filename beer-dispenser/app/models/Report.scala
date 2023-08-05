package models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

case class Report(dispenserId: UUID, times: Int, duration: Long, income: Double)
object Report {
  implicit val reportFormat: OFormat[Report] = Json.format[Report]
}
