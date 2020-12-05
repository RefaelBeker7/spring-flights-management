package com.refaelbe.springflightsmanagement.service;

import com.refaelbe.springflightsmanagement.model.Destination;
import com.refaelbe.springflightsmanagement.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationService {
    @Autowired
    DestinationRepository destinationRepository;

    public int isExistByName(String destinationName) {
        List<Destination> list = getAllDestination();
        for (Destination destination : list) {
            if (destination.getDestinationName().contains(destinationName)) {
                return (int)destination.getId();
            }
        }
        return -1;
    }

    public DestinationRepository getDestinationRepository() {
        return destinationRepository;
    }

    public List<Destination> getAllDestination() {
        List<Destination> destinations = new ArrayList<>();
        destinationRepository.findAll().forEach(destination -> destinations.add(destination));
        return destinations;
    }

    public Destination getDestinationById(long id) {
        return destinationRepository.findById(id).get();
    }

    public Destination saveOrUpdate(Destination newDestination) {

        return destinationRepository.save(newDestination);
    }

    public void delete(long id) {
        destinationRepository.deleteById(id);
    }

}
