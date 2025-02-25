package generator

import models._
import org.scalacheck.Gen

import java.time.{LocalDateTime, OffsetDateTime}
import java.time.format.DateTimeFormatter
import scala.util.Random

object Commons {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
}

object FrequentFlyerGenerator {
  val gen: Gen[FrequentFlyer] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      FrequentFlyer(FrequentFlyerId(i), "firstName", "lastName", "title",
        s"0000${i}", i, i, s"email${i}@somemail.com", s"${i+1000}",
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}

object AccountGenerator {
  val gen: Gen[Account] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Account(AccountId(i), s"user${i}","firstName", "lastName", Option(FrequentFlyerId(i)),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}

object PassengerGenerator {
  val gen: Gen[Passenger] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Passenger(PassengerId(i), BookingId(i), Option(s"booking ref${i}"), Option(i),
        "firstName", "lastName", Option(AccountId(i)),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)),
        Option(new Random().between(18, 45)))
    }
  }
}

object PhoneGenerator {
  val gen: Gen[Phone] = {
    for {
      i <- Gen.chooseNum(0, 10).map(PhoneId(_))
      n <- Gen.chooseNum(0, 10)
      m <- Gen.identifier
    } yield {
      Phone(PhoneId(i), Option(AccountId(n)), Option(s"${i+1000}"), Option("mobile"),
        Option(true), Option(OffsetDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}
