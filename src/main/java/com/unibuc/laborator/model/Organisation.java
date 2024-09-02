package com.unibuc.laborator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Organisation")
//generate code via lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String country;
    String field;
}
