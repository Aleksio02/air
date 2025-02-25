package models

import org.slf4j.LoggerFactory
import play.api.libs.json._

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import scala.util.Try

// TODO: Подключить логгер
object CustomFormats {
  val logger = LoggerFactory.getLogger("CustomFormat")


  implicit val localDateTimeFormat: Format[OffsetDateTime] = new Format[OffsetDateTime] {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZZZ")

    override def writes(o: OffsetDateTime): JsValue = {
//      logger.info(s"Got date ${o.toString}")
      println(o.toString)
      Try(o.format(formatter)).fold(t => {
        t.printStackTrace()
        throw new RuntimeException(t)
      } , JsString(_))
    }

    override def reads(json: JsValue): JsResult[OffsetDateTime] = json match {
      case JsString(value) =>
        Try(OffsetDateTime.parse(value.replace(' ', 'T'), formatter)).toEither.fold(
          _ => JsError(s"Invalid date format: $value"),
          JsSuccess(_))
      case _ => JsError("Expected string for LocalDateTime")
    }
  }

}
