package com.unibuc.laborator.controller;

import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/organisation")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;

    @GetMapping("/{id}")
    public ResponseEntity<Organisation> getOrganisationById(@PathVariable Integer id){
        return ResponseEntity.ok(organisationService.getOrganisationById(id));
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Organisation> createOrganisation(@RequestBody OrganisationRequest req){
        return ResponseEntity.ok(organisationService.createOrganisation(req.getName(), req.getCountry(), req.getFieldOfActivity()));
    }
}
