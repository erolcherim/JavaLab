package com.unibuc.laborator.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    String email;
    String firstName;
    String lastName;
    String orgId;
}
