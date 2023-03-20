package com.amigoscode.spring_course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository){
        return args -> {
            Student marian = new Student(
                    "Marian",
                    "marian@doopa.com",
                    LocalDate.of(1979, Month.JANUARY, 5)
            );

            Student zdzich = new Student(
                    "Zdzislaw",
                    "zdzichu@doopa.com",
                    LocalDate.of(1979, Month.JUNE, 5)
            );
            repository.saveAll(
                    List.of(marian, zdzich)
            );
        };
    }
}
