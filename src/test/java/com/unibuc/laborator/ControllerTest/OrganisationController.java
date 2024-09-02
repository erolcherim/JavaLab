package com.unibuc.laborator.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.controller.EndpointRequest;
import com.unibuc.laborator.controller.OrganisationRequest;
import com.unibuc.laborator.controller.UserResponse;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.service.EventService;
import com.unibuc.laborator.service.OrganisationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrganisationController {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    OrganisationService organisationService;

    @Test
    void getOrganisationByIdTest() throws Exception {
        Organisation organisation = TestDataGenerator.getTestOrg();

        when(organisationService.getOrganisationById(organisation.getId())).thenReturn(organisation);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/organisation/"+organisation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(organisation.getName()))
                .andExpect(jsonPath("$.country").value(organisation.getCountry()))
                .andExpect(jsonPath("$.field").value(organisation.getField()));
    }

    @Test
    void createOrganisationTest() throws Exception {
        Organisation organisation = TestDataGenerator.getTestOrg();

        OrganisationRequest request = OrganisationRequest.builder()
                .name(organisation.getName())
                .country(organisation.getCountry())
                .fieldOfActivity(organisation.getField())
                .build();

        when (organisationService.createOrganisation(any(), any(), any())).thenReturn(organisation);

        mockMvc.perform(post("/api/v1/organisation/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.country").value(request.getCountry()))
                .andExpect(jsonPath("$.field").value(request.getFieldOfActivity()));
    }
}
