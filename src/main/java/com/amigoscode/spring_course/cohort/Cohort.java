package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;

import java.time.LocalDate;
import java.util.List;

/**
 * Cohort class will contain a list of students signed into an instance of this class.
 *
 */
public class Cohort {
    private Long id;
    private String name;
    private List<Student> studentsList;
    private LocalDate startDate;

    public Cohort(){}

    public Cohort(Long id, String name, List<Student> studentsList, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.studentsList = studentsList;
        this.startDate = startDate;
    }

    public Cohort(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }
    void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    LocalDate getStartDate() {
        return startDate;
    }
    void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}



