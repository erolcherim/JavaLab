package com.unibuc.laborator.repository;

import com.unibuc.laborator.model.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, Integer> {
    @NonNull
    Optional<Endpoint> findById(@NonNull Integer id);
    List<Endpoint> findAllByOrganisationId(Integer id);
}
