package models

import java.time.LocalDateTime

final case class BookingId(bookingId: Int) extends AnyVal

case class Booking(bookingId: BookingId, bookingRef: String, bookingName: String, accountId: AccountId,
                   email: String, phone: String, updateTs: LocalDateTime, price: Float)

final case class BookingLegId(bookingLegId: Int) extends AnyVal

case class BookingLeg(bookingLegId: BookingLegId, bookingId: BookingId, flight: FlightId, legNum: Int,
                      isReturning: Boolean, updateTs: LocalDateTime)
