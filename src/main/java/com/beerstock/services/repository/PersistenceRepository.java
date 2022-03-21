package com.beerstock.services.repository;

import com.beerstock.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistenceRepository extends JpaRepository<Beer, Long> {
}
