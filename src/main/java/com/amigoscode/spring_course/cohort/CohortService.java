package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import com.amigoscode.spring_course.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
