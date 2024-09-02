package com.unibuc.laborator.service;

import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.repository.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndpointService {
    @Autowired
    EndpointRepository endpointRepository;
    @Autowired
    OrganisationService organisationService;

    public Endpoint getEndpointById(Integer id) {
        return endpointRepository.findById(id).orElseThrow(NotFoundCustomException::new);
    }

    public Endpoint createEndpoint(String name, String operatingSystem, String orgId) {
        var endpoint = Endpoint.builder()
                .name(name)
                .operatingSystem(operatingSystem)
                .ip(("10.17.25.").concat(String.valueOf(((int) ((Math.random() * (255)) + 0)))))
                .organisation(organisationService.getOrganisationById(Integer.valueOf(orgId)))
                .build();

        endpointRepository.save(endpoint);
        return endpoint;
    }

    // Here I avoid injecting the endpointRepo in the eventsService
    public void saveEndpointDb(Endpoint endpoint) {
        endpointRepository.save(endpoint);
    }

    public List<Endpoint> getAllEndpointsInOrg(Integer orgId) {
        return endpointRepository.findAllByOrganisationId(orgId);
    }
}

