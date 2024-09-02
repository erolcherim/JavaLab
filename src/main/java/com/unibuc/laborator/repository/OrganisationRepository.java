package com.unibuc.laborator.repository;

import com.unibuc.laborator.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    @NonNull
    Optional<Organisation> findById(@NonNull Integer id);
}
