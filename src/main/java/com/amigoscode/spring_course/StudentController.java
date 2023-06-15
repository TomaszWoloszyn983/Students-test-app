package com.amigoscode.spring_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(path = "/student")
public class StudentController {

    private final StudentService studentService;
    String infoMessage = "";

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudent(){
        return studentService.getStudents();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping("/student/login")
    public String register_success(){
        return "register";
    }

    @RequestMapping("/allStudents")
    @GetMapping
    public String allStudents(Model model){
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        model.addAttribute("studentForm", new Student());
        model.addAttribute("infoMessage", infoMessage);
        return "studentsPage";
    }

    @RequestMapping("/students")
    @GetMapping
    public String students(Model model,
                           @Param("keyword") String keyword){
        System.out.println("Initialize searching student by keyword: "+keyword);
        List<Student> students = studentService.findStudentByKeyword(keyword);
        System.out.println("Students found: "+students);
        model.addAttribute("students", students);
//        model.addAttribute("studentForm", new Student());
        model.addAttribute("keyword", keyword);
        return "studentsPage";
    }


    @PostMapping("/register")
    public String registerNewStudent(@ModelAttribute Student student, Model model,
                                     Authentication auth) throws IllegalArgumentException{
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority()
                                                        .equals("ROLE_ADMIN"))){
            try{
                model.addAttribute("studentForm", new Student());
                model.addAttribute("standardDate", new Date());
                studentService.addNewStudent(student);

                String message = "Student "+ student +" successfully added.";
                System.out.println(message);
                model.addAttribute("message", message);
                return "register";
            }catch(IllegalArgumentException e){
                System.out.println("Parse attempt failed for value");
            }
            return "register";
        }
        return "index";
    }

    /*
        This function is not used anymore
     */
    @PostMapping("/updateStudent/{studentId}/submitChanges")
    public String submitChanges(
            @PathVariable(value = "studentId") Long studentId,
            @ModelAttribute("student") Student student){
        Student studentToUpdate = studentService.findStudentById(studentId);
        studentService.updateStudent(studentId, student.getName(),
                                     student.getEmail(), student.getDob());
        return "redirect:/student/{studentId}/studentDetail";
    }

    /*
         Delete student function is secured and allowed only to users
         logged in as admins
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        this.studentService.deleteStudent(studentId);
        System.out.println("Student Service. Delete student by id "+studentId);
        infoMessage = "You have deleted Student from the database!";
        return "redirect:/student/{studentId}/studentDetail";
    }

    /*
        This function is not used anymore.

        Update student information is allowed to any logged in users.
        It should be allowed only to one user and admins.
     */
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(path = "/{studentId}/studentDetail")
    public String studentDetail(@PathVariable(value = "studentId")
                                Long studentId, Model model){
            Student studentToUpdate = studentService.findStudentById(studentId);
            model.addAttribute("student", studentToUpdate);
            infoMessage = studentToUpdate.getName()+"'s data have been updated!";
    return "studentDetail";
    }

    /**
     * Collect data from update student form displayed in the Update Modal Dialog-Box
     * Call updateStudent method from StudentService class and pass the collected data
     * to the method in order to update existing students information.
     *
     * @param name
     * @param email
     * @param date
     * @param studentId
     * @return redirect url to the students list page.
     */
    @PostMapping("/update/{studentId}")
    public String handleUpdateRequest(@RequestParam ("name") String name,
                                      @RequestParam ("email") String email,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd")
                                      @RequestParam ("date") LocalDate date,
                                      @PathVariable(value = "studentId")
                                      Long studentId) {

        studentService.updateStudent(studentId, name,
                email, date);
        System.out.println("\n\n\n!!!\nData received from modal-box.\nfor a student: "
                + studentId+"-"+name+" - "+email+" - "+date+"\n!!!\n");
        infoMessage = name+ "'s data have been updated!";

//        Pamiętaj o dodaniu do notastek wzmianki o adnotacji     @DateTimeFormat(pattern = "yyyy-MM-dd") dodanej do RequestParamsów
        return "redirect:/student/allStudents";
    }
}
