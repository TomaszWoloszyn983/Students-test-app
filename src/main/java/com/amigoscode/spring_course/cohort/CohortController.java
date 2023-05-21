package com.amigoscode.spring_course.cohort;

import com.amigoscode.spring_course.Student;
import com.amigoscode.spring_course.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/cohorts")
public class CohortController {

    private final CohortService cohortService;
    private final StudentService studentService;
    String warningMessage = "";

    public CohortController(CohortService cohortService, StudentService studentService){
        this.cohortService = cohortService;
        this.studentService = studentService;
    }
//    public StudentController(StudentService studentService){
//        this.studentService = studentService;
//    }

    @GetMapping
    public List<Cohort> getCohorts(){
        return cohortService.getCohorts();
    }

    @GetMapping("/all")
    public ModelAndView cohortPage(Model model){
        System.out.println("\nDisplay Classes Page!");

        List<Cohort> cohorts =  cohortService.getCohorts();
        List<Student> students =  studentService.getStudents();
        System.out.println("Classes List: "+cohorts);
        model.addAttribute("cohorts", cohorts);
        model.addAttribute("cohortForm", new Cohort());
        model.addAttribute("students", students);
        model.addAttribute("welcomeMessage", "Hello Muties!");
        model.addAttribute("warningMessage", warningMessage);
        ModelAndView modelAndView = new ModelAndView("cohort/cohortsPage");
        modelAndView.addObject("noClasses", "This is an example message.");
        warningMessage = "";
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
                return "redirect:/cohorts/all";
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
            @ModelAttribute("cohort") Cohort cohort) {
        System.out.println("Trying to submit changes.");
        Cohort cohortToUpdate = cohortService.findCohortById(cohortId);
        System.out.println("Submitting changes in " + cohortToUpdate.getName());
        cohortService.updateCohort(cohortId, cohort.getName(),
                cohort.getStartDate());
        return "redirect:/cohorts/all";
    }

    @PutMapping("/update/{cohortId}")
    public String handleUpdateRequest(
            @PathVariable(value = "cohortId") Long cohortId,
            Model model) {
        Cohort cohortToUpdate = cohortService.findCohortById(cohortId);
        System.out.println("Display class update modal for: "+cohortToUpdate.getId()+
                           " - "+cohortToUpdate.getName()+
                           " : "+cohortToUpdate.getStudents());
        model.addAttribute("cohortToUpdate", cohortToUpdate);
        return "redirect:/cohorts/all";
    }

    @GetMapping("/delete/{cohortId}")
    public String deleteCohort(@PathVariable("cohortId") Long cohortId){
        this.cohortService.deleteCohort(cohortId);
        System.out.println("Delete Class by id "+cohortId);
        warningMessage = "Class "+cohortId+ " deleted";
        return "redirect:/cohorts/all";
    }

    /**
     * Receive data from cohortPage form.
     * Receive id of cohort that we want to add a student to
     * and receive id of the Student that is to be added to the cohort.
     *
     * Pass the data to the CohortService to add a student to cohort
     * and to StudentService to update the Students cohort variable.
     *
     * @param cohortId
     * @param studentId
     * @param model
     * @return
     */
    @PostMapping("/addToCohort/{cohortId}")
    public String addToCohort(
            @PathVariable("cohortId") Long cohortId,
            @RequestParam("student") Long studentId,
            Model model){
        List<Student> students =  studentService.getStudents();
        Student newStudent = studentService.findStudentById(studentId);

        System.out.println("\n\n!!!\nAdd Student: "+studentId+"\nto class no."+cohortId);
        model.addAttribute("students", students);

        if (newStudent.getCohortsName() == null){
            cohortService.addToCohort(cohortId, studentId);
            studentService.addStudentToCohort(studentId, cohortId);
            warningMessage = ""+newStudent.getName()+" added to new Group";
        }else{
            warningMessage = "Student "+newStudent.getName()+" "
                    +"is already a member of "+newStudent.getCohortsName()+" team!";
        }
        return "redirect:/cohorts/all";
    }

    /**
     * Remove Student from Cohort/group
     *
     * @param cohortId
     * @param studentId
     * @param model
     * @return
     */
    @GetMapping("/removeFromCohort")
    public String removeFromCohort(
            @RequestParam("cohortId") Long cohortId,
            @RequestParam("studentId") Long studentId,
            Model model){

        cohortService.removeFromCohort(cohortId, studentId);
        studentService.removeStudentFromCohort(studentId, cohortId);
        warningMessage = "Student removed from group.";

        return "redirect:/cohorts/all";
    }
}
