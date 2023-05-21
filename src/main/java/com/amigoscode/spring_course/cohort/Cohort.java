package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Cohort class will contain a list of students signed into an instance of this class.
 *
 */
@Entity
@Table(name="cohorts")
public class Cohort implements Serializable {
    @Id
    @SequenceGenerator(
            name = "cohort_sequence",
            sequenceName = "cohort_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cohort_sequence"
    )
    private Long id;

    private String name;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "cohort")
    private Set<Student> students;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    public Cohort(){}

    public Cohort(Long id, String name, Set<Student> studentsList, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.students = studentsList;
        this.startDate = startDate;
    }

    public Cohort(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudentsList() {
        return students;
    }
    public void setStudentsList(Set<Student> studentsList) {
        this.students = studentsList;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public Set<Student> getStudents() {
        return students;
    }
    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudentToList(Student student){
        this.students.add(student);
    }

    public void removeStudentFromList(Student student){
        this.students.remove(student);
    }

    @Override
    public String toString() {
        return "" + name;
    }


    //    public String getstudentsName(){
//        if(student.getName() == null){
//            return "Freelancer";
//        }else {
//            return student.getName();
//        }
//    }
}



