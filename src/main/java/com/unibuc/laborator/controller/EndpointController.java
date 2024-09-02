package com.unibuc.laborator.controller;

import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.service.EndpointService;
import com.unibuc.laborator.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/endpoint")
public class EndpointController {
    @Autowired
    EndpointService endpointService;

    @Autowired
    TaskService taskService;

    @PostMapping("/")
    @ResponseBody
    public Endpoint createEndpoint(@RequestBody EndpointRequest req){
        return endpointService.createEndpoint(req.getName(), req.getOperatingSystem(), req.getOrgId());
    }

    @PostMapping("/task")
    @ResponseBody
    public ResponseEntity<String> assignTaskToEndpoint(@RequestBody AssignTaskRequest req){
        return ResponseEntity.ok(taskService.assignTaskToEndpoint(req.getTaskId(), req.getEndpointId()));
    }
}
