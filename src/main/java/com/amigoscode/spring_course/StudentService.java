package com.amigoscode.spring_course;

import com.amigoscode.spring_course.cohort.Cohort;
import com.amigoscode.spring_course.cohort.CohortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CohortRepository cohortRepository;

    public StudentService(StudentRepository studentRepository,
                          CohortRepository cohortRepository){
        this.studentRepository = studentRepository;
        this.cohortRepository = cohortRepository;
    }

    public List<Student> getStudents(){
        List<Student> list = studentRepository.findAll();
        return studentRepository.findAll();
    }

    //    This function can be only accessed by admin
    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken!!!");
        }
        studentRepository.save(student);

        System.out.println("New Student: "+student+" added!");
    }

    //    This function can be only accessed by admin
    public void deleteStudent(Long studentId) {
        List<Student> list = studentRepository.findAll();
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        System.out.println("Delete student by id: "+studentId);
        studentRepository.deleteById(studentId);
    }

//    This function can be only accessed by user
    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate date) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));

        if (name != null && name.length() > 0
                && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if (email != null && email.length() > 0
                && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository
                    .findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException(
                        "email taken!!!"
                );
            }
            student.setEmail(email);
        };
        student.setDob(date);
    }

    public Student findStudentById(Long studentId){
        System.out.println("Odpalamy service find student by id");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));
        System.out.println("Student: "+studentId+" found. Name: "+student.getName());
        return student;
    }

    /**
     * Finds cohort by Id.
     * Finds Student by Id
     * Update Students cohort variable with cohort.
     *
     * @param studentId
     * @param cohortId
     */
    @Transactional
    public void addStudentToCohort(Long studentId, Long cohortId){
        List<Student> list = studentRepository.findAll();
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));

        List<Cohort> cohortsList = cohortRepository.findAll();
        Cohort cohort;
        boolean existCohort = cohortRepository.existsById(cohortId);

//            cohort = cohortRepository.findCohortById(cohortId);
        cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalStateException(
                        "Class with id "+cohortId+" does not exist!"
                ));

        System.out.println("Student "+student.getName()+" assigned to "
                +cohort.getName()+" class");
        student.setCohort(cohort);
        System.out.println(student.getName()+" class is "+student.getCohort().getName()+" now.");
    }

    @Transactional
    public void removeStudentFromCohort(Long studentId, Long cohortId){
        List<Student> list = studentRepository.findAll();
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Function removeStudentFromCohort failed"+
                    "Student with id "+studentId+" does not exist!"
            );
        }
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));

        List<Cohort> cohortsList = cohortRepository.findAll();
        Cohort cohort;
        boolean existCohort = cohortRepository.existsById(cohortId);

//            cohort = cohortRepository.findCohortById(cohortId);
        cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalStateException(
                        "Class with id "+cohortId+" does not exist!"
                ));

        System.out.println("Student "+student.getName()+" assigned to "
                +cohort.getName()+" class");
        student.setCohort(null);
//        System.out.println(student.getName()+" class is "+student.getCohort().getName()+" now.");
    }
}
