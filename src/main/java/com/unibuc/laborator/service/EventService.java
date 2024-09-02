package com.unibuc.laborator.service;

import com.unibuc.laborator.controller.EventResponse;
import com.unibuc.laborator.exception.EventGenerationFailedException;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Event;
import com.unibuc.laborator.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserService userService;
    @Autowired
    EndpointService endpointService;

    public Event generateEventFromTask(Integer taskId, Endpoint endpoint) {
        var eventName = getEventActionByTaskId(taskId);
        if (!eventName.equals("")) {
            var event = Event.builder()
                    .name(eventName)
                    .generatedByUser(userService.getCurrentUser().getEmail())
                    .result(true)
                    .endpoint(endpoint)
                    .build();
            eventRepository.save(event);
            return event;
        }
        throw new EventGenerationFailedException();
    }

    public String getEventActionByTaskId(Integer taskId) {
        return switch (taskId) {
            case (1) -> "Endpoint restarted successfully";
            case (2) -> "Endpoint shut down successfully";
            case (3) -> "Endpoint updated successfully";
            case (4) ->
                    String.format("Endpoint scanned successfully. There have been %d threats found", new Random().nextInt(10));
            default -> "";
        };
    }

    public List<EventResponse> getEventsGeneratedByUser(Integer userId) {
        return convertEventListToEventResponseList(eventRepository.findAllByGeneratedByUser(userId.toString()));
    }

    public List<EventResponse> getEventsByEndpoint(Integer endpointId) {
        var endpoint = endpointService.getEndpointById(endpointId);
        return convertEventListToEventResponseList(eventRepository.findAllByEndpoint(endpoint));
    }

    public List<EventResponse> getEventsForOrganisation(Integer orgId) {
        List<Endpoint> endpointsInOrg = endpointService.getAllEndpointsInOrg(orgId);
        List<Event> events = new ArrayList<>();
        endpointsInOrg
                .forEach(e -> {
                    var evs = eventRepository.findAllByEndpoint(e);
                    events.addAll(evs);
                });
        return convertEventListToEventResponseList(events);
    }

    // mapper for Event -> Event Response
    public List<EventResponse> convertEventListToEventResponseList(List<Event> events) {
        return events.stream().map(e -> EventResponse.builder()
                .generatedByUser(e.getGeneratedByUser())
                .name(e.getName())
                .result(e.getResult())
                .build()
        ).toList();
    }
}
