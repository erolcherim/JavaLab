package com.unibuc.laborator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer taskType;
    @Size(min = 1, max = 50)
    String commonName;
}
