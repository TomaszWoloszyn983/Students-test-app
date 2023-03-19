package com.amigoscode.spring_course;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents(){
        return List.of(
                new Student(
                        1L,
                        "Zdzislaw",
                        "zdzichu@dupa.com",
                        LocalDate.of(1979, Month.JANUARY, 13),
                        44
                )
        );
    }
}
