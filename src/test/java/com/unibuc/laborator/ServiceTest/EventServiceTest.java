package com.unibuc.laborator.ServiceTest;

import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.controller.EventResponse;
import com.unibuc.laborator.exception.EventGenerationFailedException;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Event;
import com.unibuc.laborator.repository.EventRepository;
import com.unibuc.laborator.service.EndpointService;
import com.unibuc.laborator.service.EventService;
import com.unibuc.laborator.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @InjectMocks
    EventService eventService;
    @Mock
    EventRepository eventRepository;
    @Mock
    UserService userService;
    @Mock
    EventService eventServiceMock;
    @Mock
    EndpointService endpointService;

    @Test
    void generateEventFromTaskTest(){
        Integer taskId = 1;
        String eventName = "Endpoint restarted successfully";
        String generatedByUser = "test@test.com";
        lenient().when(eventServiceMock.getEventActionByTaskId(taskId)).thenReturn(eventName);
        when(userService.getCurrentUser()).thenReturn(TestDataGenerator.getTestUser());
        Boolean result = true;
        Endpoint endpoint = TestDataGenerator.getTestEndpoint();

        Event event = eventService.generateEventFromTask(taskId, endpoint);

        Assertions.assertEquals(event.getName(), eventName);
        Assertions.assertEquals(event.getGeneratedByUser(), generatedByUser);
        Assertions.assertEquals(event.getResult(), result);
        Assertions.assertEquals(event.getEndpoint(), endpoint);
    }

    @Test
    void generateEventFromTaskTest_invalidAction(){
        EventGenerationFailedException thrown = Assertions.assertThrows(
                EventGenerationFailedException.class,
                () -> eventService.generateEventFromTask(1111, TestDataGenerator.getTestEndpoint())
        );
    }

    @Test
    void generateEventActionByTaskIdTest(){
        Integer taskId = 1;
        String returnedAction = eventService.getEventActionByTaskId(taskId);
        Assertions.assertEquals(returnedAction, "Endpoint restarted successfully");
    }

    @Test
    void generateEVentActionByTaskIdTest_invalidTaskId(){
        Integer taskId = 222;
        String returnedAction = eventService.getEventActionByTaskId(taskId);
        Assertions.assertEquals(returnedAction, "");
    }

    @Test
    void getEventsGeneratedByUserTest() {
        List<Event> events = new ArrayList<>();
        events.add(TestDataGenerator.getTestEvent());
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(
                EventResponse.builder()
                .name(TestDataGenerator.getTestEvent().getName())
                .generatedByUser(TestDataGenerator.getTestEvent().getGeneratedByUser())
                .result(true)
                .build()
        );
        when(eventRepository.findAllByGeneratedByUser("1")).thenReturn(events);
        lenient().when(eventServiceMock.convertEventListToEventResponseList(events)).thenReturn(eventResponses);
        List<EventResponse> callResponse = eventService.getEventsGeneratedByUser(1);

        Assertions.assertEquals(eventResponses, callResponse);
    }

    @Test
    void getEventsByEndpointTest() {
        Endpoint testEndpoint = TestDataGenerator.getTestEndpoint();
        List<Event> events = new ArrayList<>();
        events.add(TestDataGenerator.getTestEvent());
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(
                EventResponse.builder()
                        .name(TestDataGenerator.getTestEvent().getName())
                        .generatedByUser(TestDataGenerator.getTestEvent().getGeneratedByUser())
                        .result(true)
                        .build()
        );
        when(eventRepository.findAllByEndpoint(testEndpoint)).thenReturn(events);
        lenient().when(eventServiceMock.convertEventListToEventResponseList(events)).thenReturn(eventResponses);
        when(endpointService.getEndpointById(testEndpoint.getId())).thenReturn(testEndpoint);
        List<EventResponse> callResponse = eventService.getEventsByEndpoint(testEndpoint.getId());

        Assertions.assertEquals(eventResponses, callResponse);
    }

    @Test
    void getEventsForOrganisation() {
        Endpoint testEndpoint = TestDataGenerator.getTestEndpoint();
        List<Endpoint> endpointList = new ArrayList<>();
        endpointList.add(testEndpoint);
        List<Event> events = new ArrayList<>();
        events.add(TestDataGenerator.getTestEvent());
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(
                EventResponse.builder()
                        .name(TestDataGenerator.getTestEvent().getName())
                        .generatedByUser(TestDataGenerator.getTestEvent().getGeneratedByUser())
                        .result(true)
                        .build()
        );
        when(eventRepository.findAllByEndpoint(testEndpoint)).thenReturn(events);
        lenient().when(eventServiceMock.convertEventListToEventResponseList(events)).thenReturn(eventResponses);
        when(endpointService.getAllEndpointsInOrg(TestDataGenerator.getTestOrg().getId())).thenReturn(endpointList);
        List<EventResponse> callResponse = eventService.getEventsForOrganisation(TestDataGenerator.getTestOrg().getId());

        Assertions.assertEquals(eventResponses, callResponse);
    }
}
