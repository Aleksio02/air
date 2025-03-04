package generator

import models._
import org.scalacheck.Gen

import java.time.{LocalDateTime, OffsetDateTime}
import java.time.format.DateTimeFormatter
import scala.util.Random

object Commons {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  val offsetDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
}

object FrequentFlyerGenerator {
  val gen: Gen[FrequentFlyer] = {
    for {
      id <- Gen.chooseNum(1, 10).map(FrequentFlyerId(_))
      fn <- Gen.alphaStr
      ln <- Gen.alphaStr
      t <- Gen.asciiStr
      cn <- Gen.numStr
      l <- Gen.chooseNum(5, 10)
      ap <- Gen.chooseNum(80, 100)
      em <- Gen.alphaStr
      ph <- Gen.numStr
    } yield {
      FrequentFlyer(id, fn, ln, t,
        cn, l, ap, em, ph,
        Option(OffsetDateTime.parse("2011-12-03T10:15:30+01:00", Commons.offsetDateTimeFormatter)))
    }
  }
}

object AccountGenerator {
  val gen: Gen[Account] = {
    for {
      id <- Gen.chooseNum(1, 10).map(AccountId(_))
      l <- Gen.alphaUpperStr
      fn <- Gen.alphaStr
      ln <- Gen.alphaStr
      ffId <- Gen.chooseNum(1, 10).map(FrequentFlyerId(_))
    } yield {
      Account(id, l, fn, ln, Option(ffId),
        Option(OffsetDateTime.parse("2011-12-03T10:15:30+01:00", Commons.offsetDateTimeFormatter)))
    }
  }
}

object PassengerGenerator {
  val gen: Gen[Passenger] = {
    for {
      id <- Gen.choose(1, 10).map(PassengerId(_))
      bId <- Gen.choose(5, 15).map(BookingId(_))
      bRef <- Gen.hexStr
      pNo <- Gen.const(Option(25))
      fn <- Gen.alphaStr
      ln <- Gen.alphaStr
      aId <- Gen.chooseNum(25, 30).map(AccountId(_))
      age <- Gen.const(Option(18))
    } yield {
      Passenger(id, bId, Option(bRef), pNo, fn, ln, Option(aId),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)), age)
    }
  }
}

object PhoneGenerator {
  val gen: Gen[Phone] = {
    for {
      i <- Gen.chooseNum(0, 10).map(PhoneId(_))
      aId <- Gen.option(Gen.chooseNum(30, 40).map(AccountId(_)))
      n <- Gen.chooseNum(0, 10)
      m <- Gen.identifier
      ph <- Gen.option(Gen.numStr)
      phType <- Gen.option(Gen.asciiPrintableStr)
      pph <- Gen.option(Gen.const(true))
    } yield {
      // TODO: переписать генератор!!!!
      Phone(i, aId, ph, phType, pph,
        Option(OffsetDateTime.parse("2011-12-03T10:15:30+01:00", Commons.offsetDateTimeFormatter)))
    }
  }
}
