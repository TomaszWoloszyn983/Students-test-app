package com.amigoscode.spring_course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
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
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Student with id "+studentId+" does not exist!"
            );
        }
        System.out.println("Delete student by id: "+studentId);
        studentRepository.deleteById(studentId);
    }

    public void saveStudent(Student student){
        this.studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate date) {
        System.out.println("Odpalamy serice update student");
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
        System.out.println("Service update student done");
        student.setDob(date);
        this.studentRepository.save(student);
    }

    public Student findStudentById(Long studentId){
        System.out.println("Odpalamy service find student by id");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist!"
                ));
        System.out.println("Student: "+studentId+" found.");
        return student;
    }
}
