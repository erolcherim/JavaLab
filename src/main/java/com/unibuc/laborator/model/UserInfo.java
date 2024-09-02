package com.unibuc.laborator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Size(min = 1, max = 50)
    String firstName;
    @Size(min = 1, max = 50)
    String lastName;
}
