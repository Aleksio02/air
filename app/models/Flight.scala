package models

import java.time.LocalDateTime

case class Aircraft(code: String, model: String, range: Float, aircraftClass: Int, velocity: Float)

case class Airport(airportCode: String, airportName: String, city: String, airportTz: String,
                   continent: String, isoCountry: String, isoRegion: String, intnl: Boolean,
                   updateTs: LocalDateTime)

case class Flight(flightId: Int, flightNo: String, scheduledDeparture: LocalDateTime,
                  scheduledArrival: LocalDateTime, departureAirport: Airport, arrivalAirport: Airport,
                  status: String, aircraft: Aircraft, actualDeparture: LocalDateTime,
                  actualArrival: LocalDateTime, updateTs: LocalDateTime)

case class BoardingPass(passId: Int, passenger: Passenger, bookingLeg: BookingLeg, seat: String,
                        boardingTime: LocalDateTime, precheck: Boolean, updateTs: LocalDateTime)
