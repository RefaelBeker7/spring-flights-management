package com.refaelbe.springflightsmanagement.controller;

import com.refaelbe.springflightsmanagement.model.Airline;
import com.refaelbe.springflightsmanagement.model.Destination;
import com.refaelbe.springflightsmanagement.service.AircraftService;
import com.refaelbe.springflightsmanagement.service.AirlineService;
import com.refaelbe.springflightsmanagement.service.DestinationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class MainController {
    @Autowired
    AirlineService airlineService;
    @Autowired
    AircraftService aircraftService;
    @Autowired
    DestinationService destinationService;
    Logger LOG = LoggerFactory.getLogger(MainController.class);

    // Using the @ApiResponse annotation to document other responses,
    // in addition to the regular HTTP 200 OK
    @ApiOperation(value = "View a list of available products",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })


//    2. Option to retrieve a list of airlines and their current balance.
    @GetMapping("/airline/list")
    private ResponseEntity listAllAirlines() {
        List<Airline> airlines = airlineService.getAllAirlines();
        if (airlines.isEmpty()) {
            LOG.error("Error: not found any Airline.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"not found any Airline\"}");
        } else {
            LOG.info("Info: " + airlines.toString());
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + airlines.toString());
        }
    }

    @GetMapping("/airline/{id}")
    private ResponseEntity getAirline(@PathVariable("id") long id) {
        if (!airlineService.isExistById(id)) {
            LOG.error("Error: Airline not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found\"}");
        }
        LOG.info("Info: " + airlineService.getAirlineById(id).toString());
        return ResponseEntity.ok("{\"status\": \"ok\"}\n" + airlineService.getAirlineById(id).toString());
    }

    @DeleteMapping("/airlines/delete/{id}")
    private ResponseEntity deleteAirline(@PathVariable("id") long id) {
        if (!airlineService.isExistById(id)) {
            LOG.error("Error: Airline not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found\"}");
        }
        airlineService.delete(id);
        LOG.info("Info: " + airlineService.getAirlineById(id).toString() + " Deleted!");
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }

    // 1. Option to add an airline to the system (name, initial budget, home base location).
    @PostMapping("/airline/add")
    ResponseEntity addNewAirline(@RequestBody Airline newAirline) {
        if (newAirline.getBudget().isNaN() || newAirline.getBudget() < 0) {
            LOG.error("Error: Invalid Budget.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Invalid Price\"}");
        } else {
            airlineService.getAirlineRepository().save(newAirline);
            LOG.info("Info: " + newAirline.toString()+ " Added!");
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + newAirline.toString());
        }
    }

    @PutMapping("/airline/update/{id}")
    private ResponseEntity updateAirline(@RequestBody Airline newAirline, @PathVariable long id) {
        if (!airlineService.isExistById(id)) {
            LOG.error("Error: Airline not found, please use add airline.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found, please add a new airline.\"}");
        } else {
            Airline oldAirline = airlineService.getAirlineRepository().findById(id).get();
            oldAirline.setName(newAirline.getName());
            oldAirline.setBudget(newAirline.getBudget());
            oldAirline.setBaseLocation(newAirline.getBaseLocation());
            airlineService.getAirlineRepository().save(oldAirline);
            LOG.info("Info: " + oldAirline.toString()+ " Update!");
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + oldAirline.toString());
        }
    }

    // 3. An option to add an aircraft to a specific airline (each aircraft has price and max distance[km]).
    @PostMapping("/airline/{id}/add/aircraft")
    private ResponseEntity addAircraft(@PathVariable("id") long id, @RequestBody List<Double> priceAndDistance) {
        Double price = priceAndDistance.get(0);
        Double maxDistanceKM = priceAndDistance.get(1);
        if (price.isNaN() && maxDistanceKM.isNaN()){
            LOG.error("Error: Invalid Parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Invalid Parameters\"}");
        }
        if (price < 0) {
            LOG.error("Error: Invalid Price.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Invalid Price\"}");
        }
        if (airlineService.getAirlineById(id).getBudget() < price) {
            LOG.error("Error: Not enough budget.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Not enough budget\"}");
        }
        airlineService.addAircraftToAirline(id, price, maxDistanceKM);
        LOG.info("Info: " + airlineService.getAirlineById(id).toString()+ " Aircraft added!");
        return ResponseEntity.ok("{\"status\": \"ok\"}\n" + airlineService.getAirlineById(id).toString());
    }
    // 4. An option to sell an aircraft. Upon selling an aircraft the sell price is determined as following
    //      → original price * (1 - num of months aircraft in use*0.02).
    @PostMapping("/airline/{airline_id}/sell/aircraft/{aircraft_id}")
    private ResponseEntity sellAircraft(@PathVariable("airline_id") long airline_id, @PathVariable("aircraft_id") long aircraft_id) {
        if (!airlineService.isExistById(airline_id)) {
            LOG.error("Error: Airline not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found\"}");
        } else {
            if (!airlineService.sellAircraftFromAirline(airline_id, aircraft_id)){
                LOG.error("Error: Aircraft not related to Airplane.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Aircraft to related to Airplane\"}");
            }
            LOG.info("Info: " + aircraftService.getAircraftById(aircraft_id).toString() + " Aircraft deleted!");
            airlineService.getAirlineRepository().save(airlineService.getAirlineById(airline_id));
            aircraftService.getAircraftRepository().delete(aircraftService.getAircraftById(aircraft_id));
            LOG.info("Info: " + airlineService.getAirlineById(airline_id).toString() + " Aircraft sold!");
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + airlineService.getAirlineById(airline_id).toString());
        }
    }
    // 5. Add a destination (name and location). An airline home base is considered a destination as well.
    @PostMapping("/destinations")
    private ResponseEntity addDestination(@RequestBody Destination newDestination) {
        destinationService.getDestinationRepository().save(newDestination);
        LOG.info("Info: " + newDestination.toString()+ " Added!");
        return ResponseEntity.ok("{\"status\": \"ok\"}\n" + newDestination.toString());
    }
    // 8. Provide an option to an airline to buy an aircraft from another airline.
    @PostMapping("/airline/{toAirline_id}/buy/aircraft/airline/{fromAirline_id}/{aircraft_id}")
    private ResponseEntity buyAircraftFromAirline(@PathVariable("toAirline_id") long toAirline_id,
   @PathVariable("fromAirline_id") long fromAirline_id, @PathVariable("aircraft_id") long aircraft_id) {
        if (!airlineService.isExistById(toAirline_id) && !airlineService.isExistById(fromAirline_id)) {
            LOG.error("Error: Airline not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found\"}");
        }
        if (!aircraftService.isRelatedByAirlineId(aircraft_id, fromAirline_id)) {
            LOG.error("Error: Aircraft not related to Airplane.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Aircraft not related to Airplane\"}");
        }
        if (airlineService.getAirlineById(toAirline_id).getBudget() < aircraftService.getAircraftById(aircraft_id).getPrice()) {
            LOG.error("Error: Don't have enough budget to buy aircraft.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Don't have enough  budget to buy aircraft.\"}");
        }
        airlineService.sellAircraftFromAirline(fromAirline_id, aircraft_id);
        airlineService.addAircraftFromAirline(toAirline_id, aircraft_id);
        LOG.info("Info: " + airlineService.getAirlineById(fromAirline_id).toString()+ " Aircraft sold!");
        return ResponseEntity.ok("{\"status\": \"ok\"}\n" + airlineService.getAirlineById(toAirline_id).toString());
    }

    // 9. Locations should be represented by altitude and longitude. In order to calculate distance,
    //    you should use the Haversine formula (it is highly recommended to use an existing library
    //    which implement the formula).
    // 7. An option to list the available destinations for a given airline (according to the airline,
    //    airplanes and their max distance).
    @GetMapping("/airline/{airline_id}/destination")
    private ResponseEntity getResponseEntity(@PathVariable("airline_id") long airline_id) {
        if (!airlineService.isExistById(airline_id)) {
            LOG.error("Error: Airline not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Airline not found\"}");
        }
        if (destinationService.getAllDestination().isEmpty()) {
            LOG.error("Error: Don't have any destination.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\": \"Don't have any destination\"}");
        }
        List<Destination> destinations = airlineService.getMaxDestinationForAirline(airline_id);
        if (destinations.isEmpty()) {
            LOG.info("Info: Don't have any available destinations for a given airline " );
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + "\"Don't have any available destinations for a given airline\"");
        } else {
            LOG.info("Info: " + destinations.toString());
            return ResponseEntity.ok("{\"status\": \"ok\"}\n" + destinations.toString());
        }
    }

}