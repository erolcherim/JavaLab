package com.unibuc.laborator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Size(min = 1, max = 50)
    String generatedByUser;
    @Size(min = 1, max = 50)
    String name;
    Boolean result;
    @ManyToOne
    Endpoint endpoint;
}
