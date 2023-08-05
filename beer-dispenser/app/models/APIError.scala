package models

import play.api.libs.json.{Json, OFormat}

case class APIError(errorMessage: String)
object APIError {
  implicit val apiErrorFormat: OFormat[APIError] = Json.format[APIError]
}
