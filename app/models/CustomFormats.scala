package models

import play.api.libs.json._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try}

object CustomFormats {

  implicit val localDateTimeFormat: Format[LocalDateTime] = new Format[LocalDateTime] {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS'Z'")

    override def writes(o: LocalDateTime): JsValue = JsString(o.format(formatter))

    override def reads(json: JsValue): JsResult[LocalDateTime] = json match {
      case JsString(value) =>
        Try(LocalDateTime.parse(value, formatter)).toEither.fold(
          _ => JsError(s"Invalid date format: $value"),
          JsSuccess(_))
      case _ => JsError("Expected string for LocalDateTime")
    }
  }

}
