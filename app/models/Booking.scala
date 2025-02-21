package models

import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._
import models.CustomFormats2._

import java.time.LocalDateTime

final case class BookingId(bookingId: Int) extends AnyVal
// TODO: посмотреть, как разрешаются implicit (порядок поиска)
object BookingId {
  implicit val format: Format[BookingId] = Json.valueFormat[BookingId]
}

case class Booking(bookingId: BookingId, bookingRef: String, bookingName: Option[String],
                   accountId: Option[AccountId], email: String, phone: String,
                   updateTs: Option[LocalDateTime], price: Option[Float])

object Booking {
  implicit val format: Format[Booking] = Json.format[Booking]
}

final case class BookingLegId(bookingLegId: Int) extends AnyVal

object BookingLegId {
  implicit val format: Format[BookingLegId] = Json.format[BookingLegId]
}

case class BookingLeg(bookingLegId: BookingLegId, bookingId: BookingId, flight: FlightId, legNum: Option[Int],
                      isReturning: Option[Boolean], updateTs: Option[LocalDateTime])

object BookingLeg {
  implicit val format: Format[BookingLeg] = Json.format[BookingLeg]
}
