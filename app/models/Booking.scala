package models

import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

import java.time.LocalDateTime

final case class BookingId(bookingId: Int) extends AnyVal

case class Booking(bookingId: BookingId, bookingRef: String, bookingName: Option[String], accountId: Option[AccountId],
                   email: String, phone: String, updateTs: Option[LocalDateTime], price: Option[Float])

object Booking {
  implicit val bookingReads: Reads[Booking] = (
    (JsPath \ "booking_id").read and
      (JsPath \ "booking_ref").read[String] and
      (JsPath \ "booking_name").read[String] and
      (JsPath \ "account_id").read and
      (JsPath \ "email").read[String] and
      (JsPath \ "phone").read[String] and
      (JsPath \ "update_ts").read[LocalDateTime] and
      (JsPath \ "price").read[Float]
    )

  implicit val bookingWrites: Writes[Booking] = (
    (JsPath \ "booking_id").write and
      (JsPath \ "booking_ref").write[String] and
      (JsPath \ "booking_name").write[String] and
      (JsPath \ "account_id").write and
      (JsPath \ "email").write[String] and
      (JsPath \ "phone").write[String] and
      (JsPath \ "update_ts").write[LocalDateTime] and
      (JsPath \ "price").write[Float]
    )
}

final case class BookingLegId(bookingLegId: Int) extends AnyVal

case class BookingLeg(bookingLegId: BookingLegId, bookingId: BookingId, flight: FlightId, legNum: Option[Int],
                      isReturning: Option[Boolean], updateTs: Option[LocalDateTime])

object BookingLeg {
  implicit val bookingLegReads: Reads[BookingLeg] = (
    (JsPath \ "booking_leg_id").read and
      (JsPath \ "booking_id").read and
      (JsPath \ "flight_id").read and
      (JsPath \ "leg_num").read[Int] and
      (JsPath \ "is_returning").read[Boolean] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val bookingLegWrites: Writes[BookingLeg] = (
    (JsPath \ "booking_leg_id").write and
      (JsPath \ "booking_id").write and
      (JsPath \ "flight_id").write and
      (JsPath \ "leg_num").write[Int] and
      (JsPath \ "is_returning").write[Boolean] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}
