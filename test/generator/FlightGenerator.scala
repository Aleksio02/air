package generator

import models._
import org.scalacheck.Gen

import java.time.LocalDateTime
import scala.util.Random

object AircraftGenerator {
  val gen: Gen[Aircraft] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Aircraft(AircraftCode(s"Aircraft ${new Random().between(1, 101)}"), Option("Model T"),
        new Random().between(1000f, 2300f + i), new Random().between(1, 5), 500)
    }
  }
}

object AirportGenerator {
  val gen: Gen[Airport] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Airport(AirportCode(s"Airport ${i}"), s"some airport name", "Simferopol", "GMT+3",
        Option("Europe"), Option(""), Option(""), true,
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}

object FlightGenerator {
  val gen: Gen[Flight] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      Flight(FlightId(i), s"flight no - ${i}",
        LocalDateTime.parse("2025-02-07 00:00:00", Commons.formatter),
        LocalDateTime.parse("2025-02-07 04:00:00", Commons.formatter),
        AirportCode(s"Airport ${i}"), AirportCode(s"Airport ${(i + 10) % 100}"),
        "Successful", AircraftCode(s"Aircraft ${new Random().between(1, 101)}"),
        Option(LocalDateTime.parse("2025-02-07 00:06:10", Commons.formatter)),
        Option(LocalDateTime.parse("2025-02-07 04:10:57", Commons.formatter)),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}

object BoardingPassGenerator {
  val gen: Gen[BoardingPass] = {
    for {
      i <- Gen.chooseNum(0, 100)
    } yield {
      BoardingPass(BoardingPassId(i), Option(PassengerId(i)), Option(BookingLegId(i)), Option(s"Seat ${i}"),
        Option(LocalDateTime.parse("2025-02-06 23:49:00", Commons.formatter)), Option(new Random().nextBoolean()),
        Option(LocalDateTime.parse("2025-01-01 00:00:00", Commons.formatter)))
    }
  }
}
