package com.unibuc.laborator.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.controller.EventResponse;
import com.unibuc.laborator.model.Event;
import com.unibuc.laborator.service.EventService;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    EventService eventService;

    List<EventResponse> getTestData() {
      List<EventResponse> eventResponses = new ArrayList<>();
      Event event = TestDataGenerator.getTestEvent();
      eventResponses.add(
              EventResponse.builder()
                      .name(event.getName())
                      .generatedByUser(event.getGeneratedByUser())
                      .result(event.getResult())
                      .build()

      );
        eventResponses.add(
                EventResponse.builder()
                        .name(event.getName())
                        .generatedByUser(event.getGeneratedByUser())
                        .result(event.getResult())
                        .build()

        );
      return eventResponses;
    }

    @Test
    void getEventsGeneratedByUserTest () throws Exception {
        List<EventResponse> events = this.getTestData();

        when(eventService.getEventsGeneratedByUser(TestDataGenerator.getTestUser().getId())).thenReturn(events);

        JSONParser jsonParser= new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONArray listJson = (JSONArray) jsonParser.parse(objectMapper.writeValueAsString(events));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/user/"+TestDataGenerator.getTestUser().getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").value(Matchers.containsInAnyOrder(listJson.toArray())));
    }

    @Test
    void getEventsForEndpointTest () throws Exception {
        List<EventResponse> events = this.getTestData();

        when(eventService.getEventsByEndpoint(TestDataGenerator.getTestEndpoint().getId())).thenReturn(events);

        JSONParser jsonParser= new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONArray listJson = (JSONArray) jsonParser.parse(objectMapper.writeValueAsString(events));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/endpoint/"+TestDataGenerator.getTestEndpoint().getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").value(Matchers.containsInAnyOrder(listJson.toArray())));
    }

    @Test
    void getEventsForOrganisation () throws Exception {
        List<EventResponse> events = this.getTestData();

        when(eventService.getEventsForOrganisation(TestDataGenerator.getTestOrg().getId())).thenReturn(events);

        JSONParser jsonParser= new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONArray listJson = (JSONArray) jsonParser.parse(objectMapper.writeValueAsString(events));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/organisation/"+TestDataGenerator.getTestOrg().getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").value(Matchers.containsInAnyOrder(listJson.toArray())));
    }
}

