package com.unibuc.laborator.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventResponse {
    String generatedByUser;
    String name;
    boolean result;
}
