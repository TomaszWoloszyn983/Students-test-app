package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
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

    @PostMapping("/add")
    public String registerNewCohort(@ModelAttribute Cohort cohort, Model model,
                                     Authentication auth) throws IllegalArgumentException{
//        Authentication temporarily removed
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            try{
                model.addAttribute("cohortForm", new Cohort());
                model.addAttribute("standardDate", new Date());
                cohortService.addNewCohort(cohort);

                String message = "Cohort "+ cohort +" successfully added.";
                System.out.println(message);
                model.addAttribute("message", message);
                return "register";
            }catch(IllegalArgumentException e){
                System.out.println("Parse attempt failed for value");
            }
            return "register";
//        }
//        return "index";
    }
}
