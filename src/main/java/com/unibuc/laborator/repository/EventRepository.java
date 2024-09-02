package com.unibuc.laborator.repository;

import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByGeneratedByUser(String generatedByUser);
    List<Event> findAllByEndpoint(Endpoint endpoint);
}
