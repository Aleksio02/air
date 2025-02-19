package models

import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

import java.time.LocalDateTime

final case class FrequentFlyerId(frequentFlyerId: Int) extends AnyVal

case class FrequentFlyer(frequentFlyerId: FrequentFlyerId, firstName: String, lastName: String, title: String,
                         cardNum: String, level: Int, awardPoints: Int, email: String, phone: String,
                         updateTs: Option[LocalDateTime])

object FrequentFlyer {
  implicit val frequentFlyerReads: Reads[FrequentFlyer] = (
    (JsPath \ "frequent_flyer_id").read and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "title").read[String] and
      (JsPath \ "card_num").read[String] and
      (JsPath \ "level").read[Int] and
      (JsPath \ "award_points").read[Int] and
      (JsPath \ "email").read[String] and
      (JsPath \ "phone").read[String] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val frequentFlyerWrites: Writes[FrequentFlyer] = (
    (JsPath \ "frequent_flyer_id").write and
      (JsPath \ "first_name").write[String] and
      (JsPath \ "last_name").write[String] and
      (JsPath \ "title").write[String] and
      (JsPath \ "card_num").write[String] and
      (JsPath \ "level").write[Int] and
      (JsPath \ "award_points").write[Int] and
      (JsPath \ "email").write[String] and
      (JsPath \ "phone").write[String] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}

final case class AccountId(accountId: Int) extends AnyVal

case class Account(accountId: AccountId, login: String, firstName: String, lastName: String,
                   frequentFlyerId: Option[FrequentFlyerId], updateTs: Option[LocalDateTime])

object Account {
  implicit val accountReads: Reads[Account] = (
    (JsPath \ "account_id").read and
      (JsPath \ "login").read[String] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "frequent_flyer_id").read and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val accountWrites: Writes[Account] = (
    (JsPath \ "account_id").write and
      (JsPath \ "login").write[String] and
      (JsPath \ "first_name").write[String] and
      (JsPath \ "last_name").write[String] and
      (JsPath \ "frequent_flyer_id").write and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}

final case class PassengerId(passengerId: Int) extends AnyVal

case class Passenger(passengerId: PassengerId, booking: BookingId, bookingRef: Option[String],
                     passengerNo: Option[Int], firstName: String, lastName: String,
                     accountId: Option[AccountId], updateTs: Option[LocalDateTime],
                     age: Option[Int])

object Passenger {
  implicit val passengerReads: Reads[Passenger] = (
    (JsPath \ "passenger_id").read and
      (JsPath \ "booking_id").read and
      (JsPath \ "booking_ref").read[String] and
      (JsPath \ "passenger_no").read[Int] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "account_id").read and
      (JsPath \ "update_ts").read[LocalDateTime] and
      (JsPath \ "age").read[Int]
    )

  implicit val passengerWrites: Writes[Passenger] = (
    (JsPath \ "passenger_id").write and
      (JsPath \ "booking_id").write and
      (JsPath \ "booking_ref").write[String] and
      (JsPath \ "passenger_no").write[Int] and
      (JsPath \ "first_name").write[String] and
      (JsPath \ "last_name").write[String] and
      (JsPath \ "account_id").write and
      (JsPath \ "update_ts").write[LocalDateTime] and
      (JsPath \ "age").write[Int]
    )
}

final case class PhoneId(phoneId: Int) extends AnyVal

case class Phone(phoneId: PhoneId, accountId: Option[AccountId], phone: Option[String],
                 phoneType: Option[String], primaryPhone: Option[Boolean], updateTs: Option[LocalDateTime])

object Phone {
  implicit val phoneReads: Reads[Phone] = (
    (JsPath \ "phone_id").read and
      (JsPath \ "account_id").read and
      (JsPath \ "phone").read[String] and
      (JsPath \ "phone_type").read[String] and
      (JsPath \ "primary_phone").read[Boolean] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val phoneWrites: Writes[Phone] = (
    (JsPath \ "phone_id").write and
      (JsPath \ "account_id").write and
      (JsPath \ "phone").write[String] and
      (JsPath \ "phone_type").write[String] and
      (JsPath \ "primary_phone").write[Boolean] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}