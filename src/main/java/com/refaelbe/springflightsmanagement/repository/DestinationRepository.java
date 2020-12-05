package com.refaelbe.springflightsmanagement.repository;

import com.refaelbe.springflightsmanagement.model.Destination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends CrudRepository<Destination, Long> {

}
