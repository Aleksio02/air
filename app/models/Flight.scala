package models

import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

import java.time.LocalDateTime

final case class AircraftCode(code: String) extends AnyVal
case class Aircraft(code: AircraftCode, model: Option[String], range: Float, aircraftClass: Int, velocity: Float)

object Aircraft {
  implicit val aircraftReads: Reads[Aircraft] = (
    (JsPath \ "code").read and
    (JsPath \ "model").read[String] and
    (JsPath \ "range").read[Float] and
    (JsPath \ "class").read[Int] and
    (JsPath \ "velocity").read[Float]
  )

  implicit val aircraftWrites: Writes[Aircraft] = (
    (JsPath \ "code").write and
      (JsPath \ "model").write[String] and
      (JsPath \ "range").write[Float] and
      (JsPath \ "class").write[Int] and
      (JsPath \ "velocity").write[Float]
  )
}


final case class AirportCode(airportCode: String) extends AnyVal

case class Airport(airportCode: AirportCode, airportName: String, city: String, airportTz: String,
                   continent: Option[String], isoCountry: Option[String], isoRegion: Option[String], intnl: Boolean,
                   updateTs: Option[LocalDateTime])

object Airport {
  implicit val airportReads: Reads[Airport] = (
      (JsPath \ "airport_code").read and
      (JsPath \ "airport_name").read[String] and
      (JsPath \ "city").read[String] and
      (JsPath \ "airport_tz").read[String] and
      (JsPath \ "continent").read[String] and
      (JsPath \ "iso_country").read[String] and
      (JsPath \ "iso_region").read[String] and
      (JsPath \ "intnl").read[Boolean] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val airportWrites: Writes[Airport] = (
      (JsPath \ "airport_code").write and
      (JsPath \ "airport_name").write[String] and
      (JsPath \ "city").write[String] and
      (JsPath \ "airport_tz").write[String] and
      (JsPath \ "continent").write[String] and
      (JsPath \ "iso_country").write[String] and
      (JsPath \ "iso_region").write[String] and
      (JsPath \ "intnl").write[Boolean] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}

final case class FlightId(flightId: Int) extends AnyVal

case class Flight(flightId: FlightId, flightNo: String, scheduledDeparture: LocalDateTime,
                  scheduledArrival: LocalDateTime, departureAirport: AirportCode, arrivalAirport: AirportCode,
                  status: String, aircraftCode: AircraftCode, actualDeparture: Option[LocalDateTime],
                  actualArrival: Option[LocalDateTime], updateTs: Option[LocalDateTime])

object Flight {
  implicit val flightReads: Reads[Flight] = (
    (JsPath \ "flight_id").read and
      (JsPath \ "flight_no").read[String] and
      (JsPath \ "scheduled_departure").read[LocalDateTime] and
      (JsPath \ "scheduled_arrival").read[LocalDateTime] and
      (JsPath \ "departure_airport").read and
      (JsPath \ "arrival_airport").read and
      (JsPath \ "status").read[String] and
      (JsPath \ "aircraft_code").read and
      (JsPath \ "actual_departure").read[LocalDateTime] and
      (JsPath \ "actual_arrival").read[LocalDateTime] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val flightWrites: Writes[Flight] = (
    (JsPath \ "flight_id").write and
      (JsPath \ "flight_no").write[String] and
      (JsPath \ "scheduled_departure").write[LocalDateTime] and
      (JsPath \ "scheduled_arrival").write[LocalDateTime] and
      (JsPath \ "departure_airport").write and
      (JsPath \ "arrival_airport").write and
      (JsPath \ "status").write[String] and
      (JsPath \ "aircraft_code").write and
      (JsPath \ "actual_departure").write[LocalDateTime] and
      (JsPath \ "actual_arrival").write[LocalDateTime] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}

final case class BoardingPassId(passId: Int) extends AnyVal

case class BoardingPass(passId: BoardingPassId, passenger: Option[PassengerId],
                        bookingLeg: Option[BookingLegId], seat: Option[String],
                        boardingTime: Option[LocalDateTime], precheck: Option[Boolean],
                        updateTs: Option[LocalDateTime])

object BoardingPass {
  implicit val boardingPassReads: Reads[BoardingPass] = (
    (JsPath \ "pass_id").read and
      (JsPath \ "passenger_id").read and
      (JsPath \ "booking_leg_id").read and
      (JsPath \ "seat").read[String] and
      (JsPath \ "boarding_time").read[LocalDateTime] and
      (JsPath \ "precheck").read[LocalDateTime] and
      (JsPath \ "update_ts").read[LocalDateTime]
    )

  implicit val boardingPassWrites: Writes[BoardingPass] = (
    (JsPath \ "pass_id").write and
      (JsPath \ "passenger_id").write and
      (JsPath \ "booking_leg_id").write and
      (JsPath \ "seat").write[String] and
      (JsPath \ "boarding_time").write[LocalDateTime] and
      (JsPath \ "precheck").write[LocalDateTime] and
      (JsPath \ "update_ts").write[LocalDateTime]
    )
}
