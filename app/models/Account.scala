package models

import java.time.LocalDateTime

final case class FrequentFlyerId(frequentFlyerId: Int) extends AnyVal

case class FrequentFlyer(frequentFlyerId: FrequentFlyerId, firstName: String, lastName: String, title: String,
                         cardNum: String, level: Int, awardPoints: Int, email: String, phone: String,
                         updateTs: Option[LocalDateTime])

final case class AccountId(accountId: Int) extends AnyVal

case class Account(accountId: AccountId, login: String, firstName: String, lastName: String,
                   frequentFlyerId: Option[FrequentFlyerId], updateTs: Option[LocalDateTime])

final case class PassengerId(passengerId: Int) extends AnyVal

case class Passenger(passengerId: PassengerId, booking: BookingId, bookingRef: Option[String], passengerNo: Option[Int],
                     firstName: String, lastName: String, accountId: Option[AccountId], updateTs: Option[LocalDateTime],
                     age: Option[Int])

final case class PhoneId(phoneId: Int) extends AnyVal

case class Phone(phoneId: PhoneId, accountId: Option[AccountId], phone: Option[String], phoneType: Option[String],
                 primaryPhone: Option[Boolean], updateTs: Option[LocalDateTime])
