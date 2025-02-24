package json

import generator.{AccountGenerator, AircraftGenerator, AirportGenerator, BoardingPassGenerator, BookingGenerator, BookingLegGenerator, FlightGenerator, FrequentFlyerGenerator, PassengerGenerator, PhoneGenerator}
import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import play.api.libs.json.{Format, Json}
import play.api.{Logger, Logging}
import util.TestUUtils.{createProp, doCheck}

class JsonFormatTest extends AnyFlatSpec with Logging{

  private implicit val iLogger: Logger = logger

  "FrequentFlyer" should "read and write Json" in {
    val prop = createProp(FrequentFlyerGenerator.gen)
    assert(doCheck(prop))
  }

  "Account" should "read and write Json" in {
    val prop = createProp(AccountGenerator.gen)
    assert(doCheck(prop))
  }

  "Passenger" should "read and write Json" in {
    val prop = createProp(PassengerGenerator.gen)
    assert(doCheck(prop))
  }

  "Phone" should "read and write Json" in {
    val prop = createProp(PhoneGenerator.gen)
    assert(doCheck(prop))
  }

  "Booking" should "read and write Json" in {
    val prop = createProp(BookingGenerator.gen)
    assert(doCheck(prop))
  }

  "BookingLeg" should "read and write Json" in {
    val prop = createProp(BookingLegGenerator.gen)
    assert(doCheck(prop))
  }

  "Aircraft" should "read and write Json" in {
    val prop = createProp(AircraftGenerator.gen)
    assert(doCheck(prop))
  }

  "Airport" should "read and write Json" in {
    val prop = createProp(AirportGenerator.gen)
    assert(doCheck(prop))
  }

  "Flight" should "read and write Json" in {
    val prop = createProp(FlightGenerator.gen)
    assert(doCheck(prop))
  }

  "BoardingPass" should "read and write Json" in {
    val prop = createProp(BoardingPassGenerator.gen)
    assert(doCheck(prop))
  }
}
