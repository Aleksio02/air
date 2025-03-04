package generator

import models._
import org.scalacheck.Gen

import java.time.LocalDateTime
import scala.util.Random

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
    } yield {
      Airport(c, n, ci, tz, co, isoc, isor, intnl,
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}

object FlightGenerator {
  val gen: Gen[Flight] = {
    for {
      id <- Gen.chooseNum(1, 10).map(FlightId(_))
      no <- Gen.alphaNumStr
      apc1 <- Gen.asciiPrintableStr.map(AirportCode(_))
      apc2 <- Gen.asciiPrintableStr.map(AirportCode(_))
      st <- Gen.oneOf("Successfull", "In progress", "Not started", "Cancelled")
      acc <- Gen.hexStr.map(AircraftCode(_))
    } yield {
      Flight(id, no,
        LocalDateTime.parse("2025-02-07 00:00:00", Commons.formatter),
        LocalDateTime.parse("2025-02-07 04:00:00", Commons.formatter),
        apc1, apc2, st, acc,
        Option(LocalDateTime.parse("2025-02-07 00:06:10", Commons.formatter)),
        Option(LocalDateTime.parse("2025-02-07 04:10:57", Commons.formatter)),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
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
      pc <- Gen.option(Gen.const(false))
    } yield {
      BoardingPass(id, pId, blId, s,
        Option(LocalDateTime.parse("2025-02-06 23:49:00", Commons.formatter)), pc,
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}
