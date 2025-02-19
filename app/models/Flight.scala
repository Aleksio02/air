package models

import java.time.LocalDateTime

final case class AircraftCode(code: String) extends AnyVal
case class Aircraft(code: AircraftCode, model: String, range: Float, aircraftClass: Int, velocity: Float)

final case class AirportCode(airportCode: String) extends AnyVal

case class Airport(airportCode: AirportCode, airportName: String, city: String, airportTz: String,
                   continent: String, isoCountry: String, isoRegion: String, intnl: Boolean,
                   updateTs: LocalDateTime)

final case class FlightId(flightId: Int) extends AnyVal

case class Flight(flightId: FlightId, flightNo: String, scheduledDeparture: LocalDateTime,
                  scheduledArrival: LocalDateTime, departureAirport: AirportCode, arrivalAirport: AirportCode,
                  status: String, aircraftCode: AircraftCode, actualDeparture: LocalDateTime,
                  actualArrival: LocalDateTime, updateTs: LocalDateTime)

final case class BoardingPassId(passId: Int) extends AnyVal

case class BoardingPass(passId: BoardingPassId, passenger: PassengerId, bookingLeg: BookingLegId, seat: String,
                        boardingTime: LocalDateTime, precheck: Boolean, updateTs: LocalDateTime)
