package models

import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

import java.time.LocalDateTime

final case class AircraftCode(code: String) extends AnyVal

object AircraftCode {
  implicit val format: Format[AircraftCode] = Json.format[AircraftCode]
}

case class Aircraft(code: AircraftCode, model: Option[String], range: Float, aircraftClass: Int, velocity: Float)

object Aircraft {
  implicit val format: Format[Aircraft] = Json.format[Aircraft]
}

final case class AirportCode(airportCode: String) extends AnyVal

object AirportCode {
  implicit val format: Format[AirportCode] = Json.format[AirportCode]
}

case class Airport(airportCode: AirportCode, airportName: String, city: String, airportTz: String,
                   continent: Option[String], isoCountry: Option[String], isoRegion: Option[String], intnl: Boolean,
                   updateTs: Option[LocalDateTime])

object Airport {
  implicit val format: Format[Airport] = Json.format[Airport]
}

final case class FlightId(flightId: Int) extends AnyVal

object FlightId {
  implicit val format: Format[FlightId] = Json.format[FlightId]
}

case class Flight(flightId: FlightId, flightNo: String, scheduledDeparture: LocalDateTime,
                  scheduledArrival: LocalDateTime, departureAirport: AirportCode, arrivalAirport: AirportCode,
                  status: String, aircraftCode: AircraftCode, actualDeparture: Option[LocalDateTime],
                  actualArrival: Option[LocalDateTime], updateTs: Option[LocalDateTime])

object Flight {
  implicit val format: Format[Flight] = Json.format[Flight]
}

final case class BoardingPassId(passId: Int) extends AnyVal

object BoardingPassId {
  implicit val format: Format[BoardingPassId] = Json.format[BoardingPassId]
}

case class BoardingPass(passId: BoardingPassId, passenger: Option[PassengerId],
                        bookingLeg: Option[BookingLegId], seat: Option[String],
                        boardingTime: Option[LocalDateTime], precheck: Option[Boolean],
                        updateTs: Option[LocalDateTime])

object BoardingPass {
  implicit val format: Format[BoardingPass] = Json.format[BoardingPass]
}
