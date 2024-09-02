package com.unibuc.laborator.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.controller.AssignTaskRequest;
import com.unibuc.laborator.controller.EndpointRequest;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.repository.TaskRepository;
import com.unibuc.laborator.service.EndpointService;
import com.unibuc.laborator.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EndpointControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EndpointService endpointService;
    @MockBean
    TaskService taskService;
    @MockBean
    TaskRepository taskRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createEndpoint() throws Exception {
        Endpoint endpoint = TestDataGenerator.getTestEndpoint();
        EndpointRequest request = EndpointRequest.builder()
                        .name(endpoint.getName())
                        .orgId(endpoint.getOrganisation().getId().toString())
                        .operatingSystem(endpoint.getOperatingSystem())
                        .build();

        when (endpointService.createEndpoint(any(), any(), any())).thenReturn(endpoint);

        mockMvc.perform(post("/api/v1/endpoint/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.operatingSystem").value(request.getOperatingSystem()));
    }

    @Test
    void assignTaskToEndpoint() throws Exception {
        AssignTaskRequest request = AssignTaskRequest
                .builder()
                .endpointId("1")
                .taskId("1")
                .build();
        String resultMessage = "Assigned OK";
        when(taskService.assignTaskToEndpoint(request.getTaskId(), request.getEndpointId())).thenReturn(resultMessage);
        when(taskRepository.findById(Integer.valueOf(request.getTaskId()))).thenReturn(Optional.ofNullable(TestDataGenerator.getTestTask()));
        MvcResult result = mockMvc.perform(post("/api/v1/endpoint/task")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), resultMessage);
    }
}
