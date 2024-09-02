package com.unibuc.laborator.ServiceTest;

import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.model.Task;
import com.unibuc.laborator.repository.TaskRepository;
import com.unibuc.laborator.repository.UserRepository;
import com.unibuc.laborator.service.EndpointService;
import com.unibuc.laborator.service.EventService;
import com.unibuc.laborator.service.TaskService;
import com.unibuc.laborator.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    TaskService taskService;
    @Mock
    EndpointService endpointService;
    @Mock
    EventService eventService;
    @Mock
    TaskRepository taskRepository;

    @Test
    void assignTaskToEndpointTest(){
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("TestOrg")
                .field("Testing")
                .country("Test Islands")
                .build();
        Set<Task> tasks = new HashSet<>();
        Endpoint endpoint = Endpoint.builder()
                .id(1)
                .operatingSystem("TestOS")
                .tasks(tasks)
                .ip("10.17.20.101")
                .name("Test endpoint")
                .organisation(organisation)
                .build();
        Task task = Task.builder()
                .taskType(1)
                .id(1)
                .commonName("Test task")
                .build();

        when(endpointService.getEndpointById(endpoint.getId())).thenReturn(endpoint);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.assignTaskToEndpoint(task.getId().toString(), endpoint.getId().toString());

        tasks.add(task);
        Assertions.assertEquals(endpoint.getTasks(), tasks);
    }
}
