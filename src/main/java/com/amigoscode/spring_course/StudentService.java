package com.amigoscode.spring_course;

import com.amigoscode.spring_course.cohort.Cohort;
import com.amigoscode.spring_course.cohort.CohortRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        System.out.println("Run service: Find student by id");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));
        System.out.println("Student: "+studentId+" found. Name: "+student.getName());
        return student;
    }

    /**
     * Method serves the Student List Search Box.
     * Takes the input from the search box as a parameter and return
     * a list of students that contain elements that contain parameters
     * in its name or email address.
     *
     * If the argument is NOT given or it is equals to null, the method
     * returns all students list.
     *
     * @param key
     * @return
     * @throws IllegalStateException
     */
    public Page<Student> findStudentByKeyword(int pageNumber, String sortField,
                                              String sortDir, String key)
                                              throws IllegalStateException{
        System.out.println("Run service: Find student by keyword: "+key);

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);

        if(key != null){
            try {
                return studentRepository.findStudentByKeyword(key, pageable);
            }catch(IllegalStateException isex) {
                System.out.println("Student with keyword " + key + " does not exist!");
                System.out.println();
            }catch(NullPointerException npex){
                System.out.println("Null Pointer Exception was captured in " +
                        "StudentService -> findStudentByKeyword function");
            }catch(Exception ex){
                System.out.println("Something went wrong " +
                        "in the StudentService -> findStudentByKeyword function.");
            }
        }else{
            return studentRepository.findAll(pageable);
        }
        return null;
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
