package com.amigoscode.spring_course;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
class Student implements Serializable {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @Transient
    private Integer age;
    @Transient
    private String tempDate;

    public Student(){}


    /**
     * Constructor that takes as a parameter dob in format of LocalDate
      * @param id
     * @param name
     * @param email
     * @param dob
     */
    public Student(Long id, String name, String email, LocalDate dob) {
        System.out.println("Local Date dob-1 constructor run.");
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    /**
     * Constructor that takes as a parameter dob in format of LocalDate
     * @param name
     * @param email
     * @param dob
     */
    public Student(String name, String email, LocalDate dob) {
        System.out.println("Local Date dob-2 constructor run.");
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    /**
     * Constructor that takes as a parameter dob in format of String
     * @param id
     * @param name
     * @param email
     * @param dob
     */
    public Student(Long id, String name, String email, String dob) {
        System.out.println("String dob-1 constructor run.");
        this.id = id;
        this.name = name;
        this.email = email;
        this.tempDate = dob;
//        this.dob = dob;
    }

    /**
     * Constructor that takes as a parameter dob in format of String
     * @param name
     * @param email
     * @param dob
     */
    public Student(String name, String email, String dob) {
        System.out.println("String dob-2 constructor run.");
        this.name = name;
        this.email = email;
        this.dob = setDob(dob);
//        this.dob = dob;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = Student.this.dob;
    }

    public LocalDate setDob(String dob) {
        LocalDate date = LocalDate.parse(dob);
        System.out.println("Parsing string date: "+dob+" to Localdate: "+this.dob);
        return date;
    }

    public Integer getAge() {
        System.out.println();
        if (this.dob == null){
            this.dob = LocalDate.now();
            System.out.println("Date of birth is null!");
            return 0;
        }
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

//    public String getAge() {
//        this.tempDate = LocalDate.parse(dob);
//        int age = Period.between(this.dob, LocalDate.now()).getYears();
//        String strDate = Integer.toString(age);
//        return strDate;
//    }


    @Override
    public String toString() {
        return "\nStudent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + getAge() +
                '}';
    }
}
