package com.beerstock.services.repository;

import com.beerstock.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersistenceRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByName(String name);
}
