package com.unibuc.laborator;

import com.unibuc.laborator.model.*;

import java.util.HashSet;
import java.util.Set;

public class TestDataGenerator {
    public static Organisation getTestOrg(){
        return Organisation.builder()
                .id(1)
                .name("TestOrg")
                .field("Testing")
                .country("Test Islands")
                .build();
    }

    public static Task getTestTask(){
        return Task.builder()
                .taskType(1)
                .id(1)
                .commonName("Test task")
                .build();
    }

    public static User getTestUser() {
        UserInfo userInfo = UserInfo.builder()
                .id(1)
                .firstName("Test")
                .lastName("Guy")
                .build();

        return User.builder()
                .id(1)
                .userInfo(userInfo)
                .organisation(getTestOrg())
                .email("test@test.com")
                .role(Role.USER)
                .password("Testpasswd123!")
                .build();
    }

    public static Endpoint getTestEndpoint() {
        Set<Task> tasks = new HashSet<>();
        return Endpoint.builder()
                .id(1)
                .operatingSystem("TestOS")
                .tasks(tasks)
                .ip("10.17.20.101")
                .name("Test endpoint")
                .organisation(getTestOrg())
                .build();
    }

    public static Event getTestEvent() {
        return Event.builder()
                .id(1)
                .name("test")
                .generatedByUser("test@test.com")
                .result(true)
                .endpoint(TestDataGenerator.getTestEndpoint())
                .build();
    }
}
