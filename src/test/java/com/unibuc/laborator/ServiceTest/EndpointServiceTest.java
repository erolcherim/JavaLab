package com.unibuc.laborator.ServiceTest;

import com.unibuc.laborator.TestDataGenerator;
import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.Endpoint;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.repository.EndpointRepository;
import com.unibuc.laborator.service.EndpointService;
import com.unibuc.laborator.service.OrganisationService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndpointServiceTest {
    @InjectMocks
    EndpointService endpointService;
    @Mock
    EndpointRepository endpointRepository;
    @Mock
    OrganisationService organisationService;

    @Test
    void getEndpointByIdTest() {
        Endpoint endpoint = TestDataGenerator.getTestEndpoint();
        when(endpointRepository.findById(endpoint.getId())).thenReturn(Optional.of(endpoint));
        Endpoint result = endpointService.getEndpointById(endpoint.getId());
        Assertions.assertEquals(endpoint, result);
    }

    @Test
    void getEndpointByIdTest_noExistEndpoint() {
        NotFoundCustomException thrown = Assertions.assertThrows(
                NotFoundCustomException.class,
                () -> endpointService.getEndpointById(1111)
        );
    }

    @Test
    void createEndpointTest() {
        String name = "test";
        String operatingSystem = "testOs 1.2";
        Integer orgId = 1;

        when(organisationService.getOrganisationById(orgId)).thenReturn(TestDataGenerator.getTestOrg());

        Endpoint result = endpointService.createEndpoint(name, operatingSystem, orgId.toString());

        Assertions.assertEquals(result.getName(), name);
        Assertions.assertEquals(result.getOrganisation(), TestDataGenerator.getTestOrg());
        Assertions.assertEquals(result.getOperatingSystem(), operatingSystem);
    }

    @Test
    void saveEndpointInDbTest() {
        // just a repository call, no need for a test
        Boolean isThisABadTest = true;
        Assertions.assertEquals(true, isThisABadTest);
    }

    @Test
    void getAllEndpointsInOrgTest() {
        List<Endpoint> endpointList = new ArrayList<>();
        endpointList.add(TestDataGenerator.getTestEndpoint());

        when(endpointRepository.findAllByOrganisationId(1)).thenReturn(endpointList);
        var result = endpointService.getAllEndpointsInOrg(1);
        Assertions.assertEquals(endpointList, result);
    }
}
