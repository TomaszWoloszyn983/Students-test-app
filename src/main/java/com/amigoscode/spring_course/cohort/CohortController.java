package com.amigoscode.spring_course.cohort;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/cohort")
public class CohortController {

    private final CohortService cohortService;

    public CohortController(CohortService cohortService){
        this.cohortService = cohortService;
    }

    @GetMapping
    public List<Cohort> getCohorts(){
    return cohortService.getCohorts();
    }
}
