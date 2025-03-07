package models

import org.slf4j.LoggerFactory
import play.api.libs.json._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Try

// TODO: Подключить логгер
object CustomFormats {
  val logger = LoggerFactory.getLogger("CustomFormat")


  implicit val localDateTimeFormat: Format[LocalDateTime] = new Format[LocalDateTime] {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

    override def writes(o: LocalDateTime): JsValue = {
//      logger.info(s"Got date ${o.toString}")
      println(o.toString)
      Try(o.format(formatter)).fold(t => {
        t.printStackTrace()
        throw new RuntimeException(t)
      } , JsString(_))
    }

    override def reads(json: JsValue): JsResult[LocalDateTime] = json match {
      case JsString(value) =>
        Try{
          println(s"Receieved JSValue - ${value}")
          LocalDateTime.parse(value, formatter)}.toEither.fold(
          _ => JsError(s"Invalid date format: $value"),
          JsSuccess(_))
      case _ => JsError("Expected string for LocalDateTime")
    }
  }

}
