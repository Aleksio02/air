package models

import java.time.LocalDateTime

case class FrequentFlyer(frequentFlyerId: Int, firstName: String, lastName: String, title: String,
                         cardNum: String, level: Int, awardPoints: Int, email: String, phone: String,
                         updateTs: LocalDateTime)

case class Account(accountId: Int, login: String, firstName: String, lastName: String,
                   frequentFlyer: FrequentFlyer, updateTs: LocalDateTime)


case class Passenger(passengerId: Int, booking: Booking, bookingRef: String, passengerNo: Int,
                     firstName: String, lastName: String, account: Account, updateTs: LocalDateTime,
                     age: Int)

case class Phone(phoneId: Int, account: Account, phone: String, phoneType: String,
                 primaryPhone: Boolean, updateTs: LocalDateTime)
