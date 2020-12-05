package com.refaelbe.springflightsmanagement.repository;

import com.refaelbe.springflightsmanagement.model.Airline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends CrudRepository<Airline, Long> {
}
