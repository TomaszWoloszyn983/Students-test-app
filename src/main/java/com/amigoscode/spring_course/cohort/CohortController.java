package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/cohorts")
public class CohortController {

    private final CohortService cohortService;

    public CohortController(CohortService cohortService){
        this.cohortService = cohortService;
    }

    @GetMapping
    public List<Cohort> getCohorts(){
        return cohortService.getCohorts();
    }

    @GetMapping("/all")
    public ModelAndView cohortPage(Model model){
        System.out.println("\nDisplay Classes Page!");

        List<Cohort> cohorts =  cohortService.getCohorts();
        System.out.println("Classes List: "+cohorts);
        model.addAttribute("cohorts", cohorts);
        model.addAttribute("cohortForm", new Cohort());
        ModelAndView modelAndView = new ModelAndView("cohort/cohortsPage");
        modelAndView.addObject("noClasses", "This is an example message.");
        return modelAndView;
    }

    @PostMapping("/addCohort")
    public String addNewCohort(@ModelAttribute Cohort cohort, Model model,
                                     Authentication auth) throws IllegalArgumentException{
//        Authentication temporarily removed
//        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            try{
                model.addAttribute("cohortForm", new Cohort());
                model.addAttribute("standardDate", new Date());
                cohortService.addNewCohort(cohort);

                String message = "Cohort "+ cohort.getName() +" successfully added.";
                System.out.println(message);
                model.addAttribute("message", message);
                return "register";
            }catch(IllegalArgumentException e){
                System.out.println("Parse attempt failed for value");
            }
            return "cohort/cohortsPage";
//        }
//        return "index";
    }

//    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(path = "/updateCohort/{cohortId}")
    public String updateCohort(@PathVariable(value = "cohortId")
                                        Long cohortId, Model model){
        Cohort cohortToUpdate = cohortService.findCohortById(cohortId);
        System.out.println("Update information about "+cohortToUpdate.getName());
        model.addAttribute("cohort", cohortToUpdate);
        return "cohort/updateCohort";
    }

    @PostMapping("/updateCohort/{cohortId}/submitChanges")
    public String submitChanges(
            @PathVariable(value = "cohortId") Long cohortId,
            @ModelAttribute("cohort") Cohort cohort){
        System.out.println("Trying to submit changes.");
        Cohort cohortToUpdate = cohortService.findCohortById(cohortId);
        System.out.println("Submitting changes in "+cohortToUpdate.getName());
        cohortService.updateCohort(cohortId, cohort.getName(),
                cohort.getStartDate());
        return "redirect:/cohort/cohortsPage";
    }
}
