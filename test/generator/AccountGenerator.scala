package generator

import models._
import org.scalacheck.Gen

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, OffsetDateTime, ZoneOffset}

object Commons {
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  val offsetDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
  val genOffsetDateTime: Gen[OffsetDateTime] = for {
    epochSecond <- Gen.chooseNum(1704067200L, 1735689600L)
    nano <- Gen.chooseNum(0, 999999999)
    offset <- Gen.chooseNum(-18, 18).map(ZoneOffset.ofHours)
  } yield OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSecond, nano), offset)
  val optionGenOffsetDateTime: Gen[Option[OffsetDateTime]] = Gen.option(genOffsetDateTime)

  val genLocalDateTime: Gen[LocalDateTime] = genOffsetDateTime.map(_.toLocalDateTime)
  val optionGenLocalDateTime: Gen[Option[LocalDateTime]] = Gen.option(genLocalDateTime)
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
      uts <- Commons.optionGenOffsetDateTime
    } yield {
      FrequentFlyer(id, fn, ln, t, cn, l, ap, em, ph, uts)
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
      uts <- Commons.optionGenOffsetDateTime
    } yield {
      Account(id, l, fn, ln, Option(ffId), uts)
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
      uts <- Commons.optionGenLocalDateTime
      age <- Gen.const(Option(18))
    } yield {
      Passenger(id, bId, Option(bRef), pNo, fn, ln, Option(aId),
        uts, age)
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
      uts <- Commons.optionGenLocalDateTime
    } yield {
      Phone(i, aId, ph, phType, pph, uts)
    }
  }
}
