package com.unibuc.laborator.ServiceTest;

import com.unibuc.laborator.controller.UserResponse;
import com.unibuc.laborator.exception.NotFoundCustomException;
import com.unibuc.laborator.model.Organisation;
import com.unibuc.laborator.model.Role;
import com.unibuc.laborator.model.User;
import com.unibuc.laborator.model.UserInfo;
import com.unibuc.laborator.repository.UserRepository;
import com.unibuc.laborator.service.UserService;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    User getTestUser(){
        UserInfo userInfo = UserInfo.builder()
                .id(1)
                .firstName("Test")
                .lastName("Guy")
                .build();
        Organisation organisation = Organisation.builder()
                .id(1)
                .name("TestOrg")
                .field("Testing")
                .country("Test Islands")
                .build();
        User user = User.builder()
                .userInfo(userInfo)
                .organisation(organisation)
                .email("test@test.com")
                .role(Role.USER)
                .password("Testpasswd123!")
                .build();
        return user;
    }

    @Test
    void getUserByIdTest(){
        User user = getTestUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserResponse fetchedUser = userService.getUserById(user.getId());

        Assertions.assertEquals(fetchedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(fetchedUser.getFirstName(), user.getUserInfo().getFirstName());
        Assertions.assertEquals(fetchedUser.getLastName(), user.getUserInfo().getLastName());
        Assertions.assertEquals(fetchedUser.getOrgId(), user.getOrganisation().getId().toString());
    }

    @Test
    void getUserById_userNotExist(){
        NotFoundCustomException thrown = Assertions.assertThrows(
                NotFoundCustomException.class,
                () -> userService.getUserById(1111)
        );
    }

    @Test
    void getCurrentUserTest(){
        User user = getTestUser();

        Authentication authentication = Mockito.mock(Authentication.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(String.valueOf(user.getEmail()));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User currentUser = userService.getCurrentUser();

        Assertions.assertEquals(currentUser, user);
    }
}
