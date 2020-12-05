package com.refaelbe.springflightsmanagement.repository;

import com.refaelbe.springflightsmanagement.model.Aircraft;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
