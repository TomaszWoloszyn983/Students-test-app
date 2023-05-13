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
    private final StudentRepository studentRepository;

    public CohortService(CohortRepository cohortRepository, StudentRepository studentRepository){
        this.cohortRepository = cohortRepository;
        this.studentRepository = studentRepository;
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

    /**
     * I guess that methods as existsById and deleteById are
     * inherited from JpaRepository interface and passed through
     * CohortRepository that extends JpaRepository
     *
     * @param cohortId
     */
    public void deleteCohort(Long cohortId) {
        List<Cohort> list = cohortRepository.findAll();
        boolean exists = cohortRepository.existsById(cohortId);
        if (!exists){
            throw new IllegalStateException(
                    "Class with id "+cohortId+" does not exist!"
            );
        }
        System.out.println("Delete Class by id: "+cohortId);
        cohortRepository.deleteById(cohortId);
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

    /**
     * Finds cohort by Id.
     * Finds Student by Id
     * Adds the Student to the cohorts Students List
     *
     * @param cohortId
     * @param studentId
     */
    @Transactional
    public boolean addToCohort(Long cohortId, Long studentId){
        List<Cohort> list = cohortRepository.findAll();
        Cohort cohort;
        boolean exists = cohortRepository.existsById(cohortId);
        if (!exists){
            throw new IllegalStateException(
                    "Class with id "+cohortId+" does not exist!"
            );
        }else{
            cohort = findCohortById(cohortId);
        }

        List<Student> studentsList = studentRepository.findAll();
        boolean existsStudent = studentRepository.existsById(studentId);
        if (!existsStudent){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));

        if (student.getCohort() != null) {
            System.out.println("\n!!!\nStudent "+student.getName()+" "
                    +"is already a member of "+student.getCohortsName()+" team!"
                    +"\nSo he has not been added to the list");
            return false;
        }else{
            System.out.println("New Student was added to "+cohort.getName()
                    + " Students List");
            cohort.addStudentToList(student);
            return true;
        }
    }

    @Transactional
    public void removeFromCohort(Long cohortId, Long studentId){
        List<Cohort> list = cohortRepository.findAll();
        Cohort cohort;
        boolean exists = cohortRepository.existsById(cohortId);
        if (!exists){
            throw new IllegalStateException(
                    "\nClass can't be removed form the class"+
                    "Class with id "+cohortId+" does not exist!"
            );
        }else{
            cohort = findCohortById(cohortId);
        }

        List<Student> studentsList = studentRepository.findAll();
        boolean existsStudent = studentRepository.existsById(studentId);
        if (!existsStudent){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "\nStudent can't be removed form the class"+
                        "Student with id "+studentId+" does not exist!"
                ));

        System.out.println("Student "+student.getName()+" was removed from "+cohort.getName());
        cohort.removeStudentFromList(student);
        System.out.println(cohort.getName() +" remained elements: "
                +cohort.getStudentsList());
    }
}
