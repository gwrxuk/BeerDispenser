package models

import play.api.libs.json.{Json, OFormat}

/** New Dispenser
 * Admins will create the dispenser by specifying a flow_volume.
 * This config will help to know how many liters of beer come out
 * per second and be able to calculate the total spend.
 *
 * @param flow_volume  How many liters of beer come out per second
 */
case class NewDispenser(flow_volume: Double, brand: String, price: Double)
object NewDispenser {
  implicit val newDispenserFormat: OFormat[NewDispenser] = Json.format[NewDispenser]
}
