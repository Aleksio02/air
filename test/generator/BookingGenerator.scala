package generator

import models._
import org.scalacheck.Gen

object BookingGenerator {
  val gen: Gen[Booking] = {
    for {
      id <- Gen.chooseNum(1, 10).map(BookingId(_))
      ref <- Gen.identifier
      name <- Gen.option(Gen.asciiPrintableStr)
      aId <- Gen.option(Gen.choose(1,5).map(AccountId(_)))
      em <- Gen.alphaLowerStr
      ph <- Gen.numStr
      uts <- Commons.optionGenLocalDateTime
      pr <- Gen.option(Gen.const(6500f))
    } yield {
      Booking(id, ref, name, aId, em, ph, uts, pr)
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
      uts <- Commons.optionGenLocalDateTime
    } yield {
      BookingLeg(id, bId, fId, lNum, isR, uts)
    }
  }
}
