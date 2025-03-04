package generator

import models.{AccountId, Booking, BookingId, BookingLeg, BookingLegId, FlightId}
import org.scalacheck.Gen

import java.time.LocalDateTime
import scala.util.Random

object BookingGenerator {
  val gen: Gen[Booking] = {
    for {
      id <- Gen.chooseNum(1, 10).map(BookingId(_))
      ref <- Gen.identifier
      name <- Gen.option(Gen.asciiPrintableStr)
      aId <- Gen.option(Gen.choose(1,5).map(AccountId(_)))
      em <- Gen.alphaLowerStr
      ph <- Gen.numStr
      pr <- Gen.option(Gen.const(6500f))
    } yield {
      Booking(id, ref, name, aId, em, ph,
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)),
        pr)
    }
  }
}

object BookingLegGenerator {
  val gen: Gen[BookingLeg] = {
    for {
      id <- Gen.chooseNum(7, 23).map(BookingLegId(_))
      bId <- Gen.choose(9, 18).map(BookingId(_))
      fId <- Gen.const(3).map(FlightId(_))
      lNum <- Gen.option(Gen.chooseNum(2, 12))
      isR <- Gen.option(Gen.const(false))
    } yield {
      BookingLeg(id, bId, fId, lNum,
        isR,
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}
