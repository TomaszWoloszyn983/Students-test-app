package com.amigoscode.spring_course.cohort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CohortService {

    private final CohortRepository cohortRepository;

    public CohortService(CohortRepository cohortRepository){
        this.cohortRepository = cohortRepository;
    }

    public List<Cohort> getCohorts(){
        List<Cohort> list = cohortRepository.findAll();
        return cohortRepository.findAll();
    }

    //    This function can be only accessed by admin
    public void addNewCohort(Cohort cohort) {
        Optional<Cohort> cohortOptional = cohortRepository
                .findCohortByName(cohort.getName());
        if (cohortOptional.isPresent()){
            throw new IllegalStateException("email taken!!!");
        }
        cohortRepository.save(cohort);

        System.out.println("New Cohort: "+cohort+" added!");
    }
}
