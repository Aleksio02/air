package models

import play.api.libs.json.{Format, JsResult, JsValue}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object CustomFormats2 {
  implicit val localDateTimeFormat2: Format[LocalDateTime] = Format(
    Format.of[String].map(LocalDateTime.parse(_, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))),
    Format.of[String].contramap(_.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
  )

}
