package com.unibuc.laborator.ServiceTest;

import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.repository.OrganisationRepository;
import com.unibuc.laborator.service.OrganisationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganisationServiceTest {
    @InjectMocks
    OrganisationService organisationService;
    @Mock
    OrganisationRepository organisationRepository;

    Organisation getTestDataOrg() {
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("TestOrg")
                .field("Testing")
                .country("Test Islands")
                .build();

        return organisation;
    }

    @Test
    void getOrganisationById(){
        Organisation organisation = getTestDataOrg();

        when(organisationRepository.findById(organisation.getId())).thenReturn(Optional.of(organisation));

        Organisation fetchedOrg = organisationService.getOrganisationById(organisation.getId());
        Assertions.assertEquals(fetchedOrg, organisation);
    }

    @Test
    void getOrganisationById_orgNotExist(){
        NotFoundCustomException thrown = Assertions.assertThrows(
                NotFoundCustomException.class,
                () -> organisationService.getOrganisationById(1111)
        );
    }

    @Test
    void createOrganisationTest(){
        String name = "tst";
        String country = "tst2";
        String fieldOfActivity = "tst3";

        var createdOrg = organisationService.createOrganisation(name, country, fieldOfActivity);
        Assertions.assertEquals(createdOrg.getName(), name);
        Assertions.assertEquals(createdOrg.getCountry(), country);
        Assertions.assertEquals(createdOrg.getField(), fieldOfActivity);
    }

}
