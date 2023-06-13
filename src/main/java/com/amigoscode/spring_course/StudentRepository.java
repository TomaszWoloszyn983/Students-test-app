package com.amigoscode.spring_course;

import com.amigoscode.spring_course.cohort.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long> {

//    SELECT * FROM student WHERE email = ?
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.name = ?1")
    Optional<Student> findStudentByName(String name);

    @Query("SELECT s FROM Student s WHERE s.cohort = ?1")
    Optional<Student> findStudentByCohort(Cohort cohort);

    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Optional<Student> findStudentById(Long studentId);

    @Query("SELECT s FROM Student s WHERE s.name LIKE %?1%"
            + "OR s.email LIKE %?1%"
//            + "OR s.cohort LIKE %?1%"
    )
    List<Student> findStudentByKeyword(String key);
}
