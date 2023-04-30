package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import com.amigoscode.spring_course.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CohortService {

    private final CohortRepository cohortRepository;

    /*
    * Instance of student repository may be need to create
    * addStudent to a class function for identifying students
    * to be added to a class.
    * */
//    private final StudentRepository studentRepository;

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

        System.out.println("New Cohort: "+cohort.getName()+" added!");
    }

    @Transactional
    public void updateCohort(Long cohortId, String name, LocalDate date) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalStateException(
                        "Cohort with id "+cohortId+" does not exist!"
                ));

        if (name != null && name.length() > 0
                && !Objects.equals(cohort.getName(), name)){
            cohort.setName(name);
        }
        cohort.setStartDate(date);
    }

    public Cohort findCohortById(Long cohortId){
        System.out.println("Odpalamy service find cohort by id");
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalStateException(
                        "Class with id "+cohortId+" does not exist!"
                ));
        System.out.println("Class: "+cohortId+" found. Name: "+cohort.getName());
        return cohort;
    }
}
