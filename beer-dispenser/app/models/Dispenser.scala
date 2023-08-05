package models

import play.api.libs.json.{Json, OFormat}

import java.util.UUID

/** Dispenser
 * flow_volume help to know how many liters of beer come out
 * per second and be able to calculate the total spend.
 *
 * @param id  Dispenser Id
 * @param flow_volume  How many liters of beer come out per second
 */
case class Dispenser(id: UUID, flow_volume: Double, is_open: Boolean, updated_at: Long, brand: String, price: Double)
object Dispenser {
  implicit val dispenserFormat: OFormat[Dispenser] = Json.format[Dispenser]
}
