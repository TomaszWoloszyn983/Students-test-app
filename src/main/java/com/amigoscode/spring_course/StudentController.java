package com.amigoscode.spring_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(path = "/student")
public class StudentController {

    private final StudentService studentService;

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
        List<Student> students =  studentService.getStudents();
        model.addAttribute("students", students);
        model.addAttribute("studentForm", new Student());
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

    @PostMapping("/updateStudent/{studentId}/submitChanges")
    public String submitChanges(
            @PathVariable(value = "studentId") Long studentId,
            @ModelAttribute("student") Student student){
        Student studentToUpdate = studentService.findStudentById(studentId);
        studentService.updateStudent(studentId, student.getName(),
                                     student.getEmail(), student.getDob());
        return "redirect:/student/allStudents";
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
        return "redirect:/student/allStudents";
    }

    /*
        Update student information is allowed to any logged in users.
        It should be allowed only to one user and admins.
     */
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(path = "/updateStudent/{studentId}")
    public String updateStudent(@PathVariable(value = "studentId")
                                Long studentId, Model model){
            Student studentToUpdate = studentService.findStudentById(studentId);
            model.addAttribute("student", studentToUpdate);
    return "updateStudent";
    }

    @PostMapping("/update/{studentId}")
    public Model handleUpdateRequest(@ModelAttribute("student")
                                      Student student,
                                      @PathVariable(value = "studentId")
                                      Long studentId, Model model) {
        Student studentToUpdate = studentService.findStudentById(studentId);
        System.out.println("\n\n\n!!!\nHandleUpdateRequest method was launched\nfor a student: "
                + studentToUpdate.getId()+" - "+studentToUpdate.getName()+"\n!!!\n");
        model.addAttribute("student", studentToUpdate);
//        Może trzeba tutaj zwrócić formularz
        return model;
    }
}
