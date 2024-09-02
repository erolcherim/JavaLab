package com.unibuc.laborator.controller;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointRequest {
    String name;
    String operatingSystem;
    String orgId;
}
