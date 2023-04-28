package com.amigoscode.spring_course.cohort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long> {
    //    SELECT * FROM cohort WHERE name = ?

    @Query("SELECT c FROM Cohort c WHERE c.id = ?1")
    Optional<Cohort> findCohortById(Long cohortId);

    @Query("SELECT c FROM Cohort c WHERE c.name = ?1")
    Optional<Cohort> findCohortByName(String cohortName);
}
