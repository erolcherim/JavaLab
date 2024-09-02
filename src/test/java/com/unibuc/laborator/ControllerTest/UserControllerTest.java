package com.unibuc.laborator.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.controller.UserResponse;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;

    @Test
    void getUserByIdTest() throws Exception {
        User user = TestDataGenerator.getTestUser();
        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .orgId(user.getOrganisation().getId().toString())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .build();

        when(userService.getUserById(user.getId())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/"+user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(jsonPath("$.orgId").value(userResponse.getOrgId()))
                .andExpect(jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userResponse.getLastName()));
    }
}
