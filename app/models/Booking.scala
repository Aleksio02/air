package models

import java.time.LocalDateTime

case class Booking(bookingId: Int, bookingRef: String, bookingName: String, account: Account,
                   email: String, phone: String, updateTs: LocalDateTime, price: Float)


case class BookingLeg(bookingLegId: Int, booking: Booking, flight: Flight, legNum: Int,
                      isReturning: Boolean, updateTs: LocalDateTime)
