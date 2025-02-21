package models

import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object CustomFormats {

  implicit val localDateTimeFormat: Format[LocalDateTime] = new Format[LocalDateTime] {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override def writes(o: LocalDateTime): JsValue = JsString(o.format(formatter))

    override def reads(json: JsValue): JsResult[LocalDateTime] = json match {
      case JsString(value) =>
        try {
          JsSuccess(LocalDateTime.parse(value, formatter))
        } catch {
          case e: Exception => JsError(s"Invalid date format: $value")
        }
      case _ => JsError("Expected string for LocalDateTime")
    }
  }

}
