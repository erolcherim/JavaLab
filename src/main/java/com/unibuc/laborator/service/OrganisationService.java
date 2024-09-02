package com.unibuc.laborator.service;

import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
    @Autowired
    OrganisationRepository organisationRepository;

    public Organisation getOrganisationById(Integer id){
        return organisationRepository.findById(id).orElseThrow(NotFoundCustomException::new);
    }

    public Organisation createOrganisation(String name, String country, String field) {
        Organisation org = Organisation.builder()
                .name(name)
                .country(country)
                .field(field)
                .build();
        organisationRepository.save(org);
        return org;
    }
}
