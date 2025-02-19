package models

import java.time.LocalDateTime

final case class AircraftCode(code: String) extends AnyVal
case class Aircraft(code: AircraftCode, model: Option[String], range: Float, aircraftClass: Int, velocity: Float)

final case class AirportCode(airportCode: String) extends AnyVal

case class Airport(airportCode: AirportCode, airportName: String, city: String, airportTz: String,
                   continent: Option[String], isoCountry: Option[String], isoRegion: Option[String], intnl: Boolean,
                   updateTs: Option[LocalDateTime])

final case class FlightId(flightId: Int) extends AnyVal

case class Flight(flightId: FlightId, flightNo: String, scheduledDeparture: LocalDateTime,
                  scheduledArrival: LocalDateTime, departureAirport: AirportCode, arrivalAirport: AirportCode,
                  status: String, aircraftCode: AircraftCode, actualDeparture: Option[LocalDateTime],
                  actualArrival: Option[LocalDateTime], updateTs: Option[LocalDateTime])

final case class BoardingPassId(passId: Int) extends AnyVal

case class BoardingPass(passId: BoardingPassId, passenger: Option[PassengerId],
                        bookingLeg: Option[BookingLegId], seat: Option[String],
                        boardingTime: Option[LocalDateTime], precheck: Option[Boolean],
                        updateTs: Option[LocalDateTime])
