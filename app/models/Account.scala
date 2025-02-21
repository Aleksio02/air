package models

import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

import java.time.LocalDateTime

final case class FrequentFlyerId(frequentFlyerId: Int) extends AnyVal

object FrequentFlyerId {
  implicit val format: Format[FrequentFlyerId] = Json.format[FrequentFlyerId]
}

case class FrequentFlyer(frequentFlyerId: FrequentFlyerId, firstName: String, lastName: String, title: String,
                         cardNum: String, level: Int, awardPoints: Int, email: String, phone: String,
                         updateTs: Option[LocalDateTime])

object FrequentFlyer {
  implicit val format: Format[FrequentFlyer] = Json.format[FrequentFlyer]
}

final case class AccountId(accountId: Int) extends AnyVal

object AccountId {
  implicit val format: Format[AccountId] = Json.format[AccountId]
}

case class Account(accountId: AccountId, login: String, firstName: String, lastName: String,
                   frequentFlyerId: Option[FrequentFlyerId], updateTs: Option[LocalDateTime])

object Account {
  implicit val format: Format[Account] = Json.format[Account]
}

final case class PassengerId(passengerId: Int) extends AnyVal

object PassengerId {
  implicit val format: Format[PassengerId] = Json.format[PassengerId]
}

case class Passenger(passengerId: PassengerId, booking: BookingId, bookingRef: Option[String],
                     passengerNo: Option[Int], firstName: String, lastName: String,
                     accountId: Option[AccountId], updateTs: Option[LocalDateTime],
                     age: Option[Int])

object Passenger {
  implicit val format: Format[Passenger] = Json.format[Passenger]
}

final case class PhoneId(phoneId: Int) extends AnyVal

object PhoneId {
  implicit val format: Format[PhoneId] = Json.format[PhoneId]
}

case class Phone(phoneId: PhoneId, accountId: Option[AccountId], phone: Option[String],
                 phoneType: Option[String], primaryPhone: Option[Boolean], updateTs: Option[LocalDateTime])


object Phone {
  implicit val format: Format[Phone] = Json.format[Phone]
}