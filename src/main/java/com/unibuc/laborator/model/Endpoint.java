package com.unibuc.laborator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name="Endpoint")
//generate code via lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Size(min = 1, max = 50)
    String name;
    @Size(min = 1, max = 50)
    String operatingSystem;
    @Size(min = 1, max = 50)
    String ip;

    @ManyToOne
    Organisation organisation;

    @ManyToMany
    @JoinTable(name = "endpoint_task",
            joinColumns = @JoinColumn(name = "endpoint_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    Set<Task> tasks;
}
