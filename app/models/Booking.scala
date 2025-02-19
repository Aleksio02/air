package models

import java.time.LocalDateTime

final case class BookingId(bookingId: Int) extends AnyVal

case class Booking(bookingId: BookingId, bookingRef: String, bookingName: Option[String], accountId: Option[AccountId],
                   email: String, phone: String, updateTs: Option[LocalDateTime], price: Option[Float])

final case class BookingLegId(bookingLegId: Int) extends AnyVal

case class BookingLeg(bookingLegId: BookingLegId, bookingId: BookingId, flight: FlightId, legNum: Option[Int],
                      isReturning: Option[Boolean], updateTs: Option[LocalDateTime])
