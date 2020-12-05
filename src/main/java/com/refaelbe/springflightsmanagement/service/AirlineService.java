package com.refaelbe.springflightsmanagement.service;

import com.refaelbe.springflightsmanagement.model.Aircraft;
import com.refaelbe.springflightsmanagement.model.Airline;
import com.refaelbe.springflightsmanagement.model.Destination;
import com.refaelbe.springflightsmanagement.repository.AircraftRepository;
import com.refaelbe.springflightsmanagement.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    AircraftRepository aircraftRepository;
    @Autowired
    DestinationService destinationService;
    @Autowired
    AircraftService aircraftService;

    public AirlineRepository getAirlineRepository() {
        return airlineRepository;
    }

    public boolean isExistById(long id) {
        Optional<Airline> airline = airlineRepository.findById(id);
        return airline.isPresent();
    }

    public boolean isExistByAirline(Airline airlineToCheck) {
        return airlineToCheck.getBaseLocation() != null && airlineToCheck.getAircraftKeys() != null;
    }

    public void saveOrUpdate(Airline airline) {
        airlineRepository.save(airline);
    }

    public List<Airline> getAllAirlines() {
        List<Airline> airlines = new ArrayList<Airline>();
        airlineRepository.findAll().forEach(airline -> airlines.add(airline));
        return airlines;
    }

    public Airline getAirlineById(long id) { return airlineRepository.findById(id).get(); }

    public void delete(long id) {
        airlineRepository.deleteById(id);
    }

    public void addAircraftToAirline(long airline_id, Double price, Double maxDistanceKM) {
        Aircraft aircraft = new Aircraft(airline_id, price, maxDistanceKM);
        getAirlineById(airline_id).setBudget(getAirlineById(airline_id).getBudget() - price);
        addAircraft(getAirlineById(airline_id), aircraft);
    }

    public void addAircraft(Airline airline, Aircraft aircraft) {
        aircraft.setAirlineId(airline.getId());
        aircraftRepository.save(aircraft);
        List<Integer> newAircraftKeys = airline.getAircraftKeys();
        newAircraftKeys.add((int)aircraft.getId());
        airline.setAircraftKeys(newAircraftKeys);
        airlineRepository.save(airline);
    }

    public void addAircraftFromAirline(long airline_id, long aircraft_id) {
        getAirlineById(airline_id).setBudget(getAirlineById(airline_id).getBudget() - getRelevantPrice(getAirlineById(airline_id), aircraft_id));
        addAircraft(getAirlineById(airline_id), aircraftService.getAircraftById(aircraft_id));
    }

    public boolean sellAircraftFromAirline(long airline_id, long aircraft_id) {
        Aircraft aircraft = aircraftService.getAircraftById(aircraft_id);
        Airline airline =  getAirlineById(airline_id);
        List<Integer> listOfAircraft = airline.getAircraftKeys();
        int index = airline.getAircraftKeys().indexOf((int)aircraft_id);
        if (index == -1) {
            return false;
        }
        airline.setBudget(airline.getBudget() + getRelevantPrice(airline, aircraft.getId()));
        listOfAircraft.remove(Integer.valueOf((int) aircraft_id));
        airline.setAircraftKeys(listOfAircraft);
        return true;
    }

    public double getRelevantPrice (Airline airline, long aircraft_id) {
        return aircraftService.getAircraftById(aircraft_id).getPrice() * (1.0 -
                                (numberMonthUse(airline, aircraft_id) * 0.02));
    }

    public double numberMonthUse(Airline airline, long aircraft_id){
        Calendar cal = Calendar.getInstance();
        Aircraft aircraft = aircraftService.getAircraftById(aircraft_id);
        if (aircraft.getCreateAtYear() == cal.get(cal.YEAR) && aircraft.getCreateAtMonth() == cal.get(cal.MONTH)) {
            return 0.01;
        } else {
            if (aircraft.getCreateAtYear() == cal.get(cal.YEAR)) {
                return (aircraft.getCreateAtMonth() - cal.get(cal.MONTH));
            } else {
                return (12 * (aircraft.getCreateAtYear() - cal.get(cal.YEAR)) +
                        ((aircraft.getCreateAtMonth() - cal.get(cal.MONTH))));
            }
        }
    }

    public List<Destination> getMaxDestinationForAirline(long airline_id) {
        int calculateHaversineDistance = 0;
        Aircraft aircraft;
        Airline airline =  getAirlineById(airline_id);
        List<Destination> destinationsForAirline = new ArrayList<>();
        List<Destination> destinations = destinationService.getAllDestination();
        List<Integer> listOfAircraft = airline.getAircraftKeys();
        for (Destination destination : destinations) {
            for (Integer aircraftKey : listOfAircraft) {
                aircraft = aircraftService.getAircraftById(aircraftKey);
                calculateHaversineDistance =
                        calculateHaversineDistanceAlgorithm(airline.getBaseLocation().getLatitude(),
                                airline.getBaseLocation().getLongitude(), destination.getLatitude(),
                                destination.getLongitude());
                if (aircraft.getMaxDistanceKM() >= calculateHaversineDistance)
                    destinationsForAirline.add(destination);
            }
        }
        return destinationsForAirline;
    }
    /*
 ---- This is the implementation Haversine Distance Algorithm between two places
   R = earth’s radius (mean radius = 6,371km)
    Δlat = lat2− lat1
    Δlong = long2− long1
    a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
    c = 2.atan2(√a, √(1−a))
    d = R.c
    */
    public int calculateHaversineDistanceAlgorithm
            (double baseLatitude, double baseLongitude, double venueLatitude, double venueLongitude) {
        double AVERAGE_RADIUS_OF_EARTH = 6371;
        double latitudeDistance = Math.toRadians(baseLatitude - venueLatitude);
        double longitudeDistance = Math.toRadians(baseLongitude - venueLongitude);

        double a = (Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)) +
                (Math.cos(Math.toRadians(baseLatitude))) *
                        (Math.cos(Math.toRadians(venueLatitude))) *
                        (Math.sin(longitudeDistance / 2)) *
                        (Math.sin(longitudeDistance / 2));

        double C = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * C));
    }

}

