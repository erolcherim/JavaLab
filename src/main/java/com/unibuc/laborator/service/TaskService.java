package com.unibuc.laborator.service;

import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    // no CRUD operations here, we insert some default values in db via a data.sql script
    @Autowired
    EndpointService endpointService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EventService eventService;

    public String assignTaskToEndpoint(String taskId, String endpointId){
        var endpoint = endpointService.getEndpointById(Integer.valueOf(endpointId));
        var tasks = endpoint.getTasks();
        tasks.add(taskRepository.findById(Integer.valueOf(taskId)).orElseThrow(NotFoundCustomException::new));
        endpoint.setTasks(tasks);
        endpointService.saveEndpointDb(endpoint);
        eventService.generateEventFromTask(Integer.valueOf(taskId), endpoint);
        return "Assigned OK";
    }
}
