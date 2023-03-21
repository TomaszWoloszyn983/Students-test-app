package com.amigoscode.spring_course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        List<Student> list = studentRepository.findAll();
        System.out.println("\n\n List of all students:");
        for(Student student : list){
            System.out.println(student);
        }
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        getStudents();
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken!!!");
        }
        studentRepository.save(student);

        System.out.println("New Student: "+student+" added!");
    }

    public void deleteStudent(Long studentId) {
        List<Student> list = studentRepository.findAll();
        System.out.println("\n\n List of all students:");
        for(Student student : list){
            System.out.println(student);
        }
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        System.out.println("Delete student by id: "+studentId);
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
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
    }
}
