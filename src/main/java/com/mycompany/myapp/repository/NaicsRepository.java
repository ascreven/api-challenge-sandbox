package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Naics;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Naics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaicsRepository extends JpaRepository<Naics, Long> {
}
