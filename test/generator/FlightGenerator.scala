package generator

import models._
import org.scalacheck.Gen

object AircraftGenerator {
  val gen: Gen[Aircraft] = {
    for {
      c <- Gen.hexStr.map(AircraftCode(_))
      m <- Gen.option(Gen.alphaUpperStr)
      r <- Gen.chooseNum(1000f, 2300f)
      cl <- Gen.choose(1, 5)
      v <- Gen.const(500)
    } yield {
      Aircraft(c, m, r, cl, v)
    }
  }
}

object AirportGenerator {
  val gen: Gen[Airport] = {
    for {
      c <- Gen.alphaNumStr.map(AirportCode(_))
      n <- Gen.identifier
      ci <- Gen.alphaLowerStr
      tz <- Gen.numStr
      co <- Gen.option(Gen.asciiPrintableStr)
      isoc <- Gen.option(Gen.const(""))
      isor <- Gen.option(Gen.const(""))
      intnl <- Gen.const(true)
      uts <- Commons.optionGenLocalDateTime
    } yield {
      Airport(c, n, ci, tz, co, isoc, isor, intnl, uts)
    }
  }
}

object FlightGenerator {
  val gen: Gen[Flight] = {
    for {
      id <- Gen.chooseNum(1, 10).map(FlightId(_))
      no <- Gen.alphaNumStr
      schDep <- Commons.genLocalDateTime
      schArr <- Commons.genLocalDateTime
      apc1 <- Gen.asciiPrintableStr.map(AirportCode(_))
      apc2 <- Gen.asciiPrintableStr.map(AirportCode(_))
      st <- Gen.oneOf("Successfull", "In progress", "Not started", "Cancelled")
      acc <- Gen.hexStr.map(AircraftCode(_))
      actDep <- Commons.optionGenLocalDateTime
      actArr <- Commons.optionGenLocalDateTime
      uts <- Commons.optionGenLocalDateTime
    } yield {
      Flight(id, no, schDep, schArr, apc1, apc2, st, acc, actDep, actArr, uts)
    }
  }
}

object BoardingPassGenerator {
  val gen: Gen[BoardingPass] = {
    for {
      id <- Gen.chooseNum(1, 10).map(BoardingPassId(_))
      pId <- Gen.option(Gen.choose(11, 19).map(PassengerId(_)))
      blId <- Gen.option(Gen.choose(11, 19).map(BookingLegId(_)))
      s <- Gen.option(Gen.identifier)
      bt <- Commons.optionGenLocalDateTime
      pc <- Gen.option(Gen.const(false))
      uts <- Commons.optionGenLocalDateTime
    } yield {
      BoardingPass(id, pId, blId, s, bt, pc, uts)
    }
  }
}
