package com.unibuc.laborator.controller;

import com.unibuc.laborator.model.Event;
import com.unibuc.laborator.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<EventResponse>> getEventsGeneratedByUser(@PathVariable Integer id){
        return ResponseEntity.ok(eventService.getEventsGeneratedByUser(id));
    }
    @GetMapping("/endpoint/{id}")
    public ResponseEntity<List<EventResponse>> getEventsForEndpoint(@PathVariable Integer id){
        return ResponseEntity.ok(eventService.getEventsByEndpoint(id));
    }
    @GetMapping("/organisation/{id}")
    public ResponseEntity<List<EventResponse>> getEventsForOrganisation(@PathVariable Integer id){
        return ResponseEntity.ok(eventService.getEventsForOrganisation(id));
    }
}
