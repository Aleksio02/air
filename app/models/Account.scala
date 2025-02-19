package models

import java.time.LocalDateTime

final case class FrequentFlyerId(frequentFlyerId: Int) extends AnyVal

case class FrequentFlyer(frequentFlyerId: FrequentFlyerId, firstName: String, lastName: String, title: String,
                         cardNum: String, level: Int, awardPoints: Int, email: String, phone: String,
                         updateTs: LocalDateTime)

final case class AccountId(accountId: Int) extends AnyVal

case class Account(accountId: AccountId, login: String, firstName: String, lastName: String,
                   frequentFlyerId: FrequentFlyerId, updateTs: LocalDateTime)

final case class PassengerId(passengerId: Int) extends AnyVal

case class Passenger(passengerId: PassengerId, booking: BookingId, bookingRef: String, passengerNo: Int,
                     firstName: String, lastName: String, accountId: AccountId, updateTs: LocalDateTime,
                     age: Int)

final case class PhoneId(phoneId: Int) extends AnyVal

case class Phone(phoneId: PhoneId, accountId: AccountId, phone: String, phoneType: String,
                 primaryPhone: Boolean, updateTs: LocalDateTime)
