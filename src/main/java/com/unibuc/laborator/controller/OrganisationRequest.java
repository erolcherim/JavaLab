package com.unibuc.laborator.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationRequest {
    String name;
    String country;
    String fieldOfActivity;
}
