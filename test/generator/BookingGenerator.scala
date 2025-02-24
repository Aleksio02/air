package generator

import models.{AccountId, Booking, BookingId, BookingLeg, BookingLegId, FlightId}
import org.scalacheck.Gen

import java.time.LocalDateTime
import scala.util.Random

object BookingGenerator {
  val gen: Gen[Booking] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Booking(BookingId(i), "some booking ref", Option("some booking name"), Option(AccountId(i)),
        s"email${i}@somemail.com", s"${i + 1000}", Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)),
        Option(6500f))
    }
  }
}

object BookingLegGenerator {
  val gen: Gen[BookingLeg] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      BookingLeg(BookingLegId(i), BookingId(i), FlightId(i), Option(i),
        Option(new Random().nextBoolean()),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}
