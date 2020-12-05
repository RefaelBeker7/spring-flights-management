package com.refaelbe.springflightsmanagement.service;

import com.refaelbe.springflightsmanagement.model.Aircraft;
import com.refaelbe.springflightsmanagement.model.Airline;
import com.refaelbe.springflightsmanagement.repository.AircraftRepository;
import com.refaelbe.springflightsmanagement.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {
    @Autowired
    AircraftRepository aircraftRepository;

    public AircraftRepository getAircraftRepository() {
        return aircraftRepository;
    }

    public boolean isRelatedByAirlineId(long aircraftId, long airlineId) {
        return airlineId == getAircraftById(aircraftId).getAirlineId();
    }

    public Aircraft getAircraftById(long id) {
        return aircraftRepository.findById(id).get();
    }

}
