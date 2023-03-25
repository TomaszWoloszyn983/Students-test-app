package com.amigoscode.spring_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(path = "api/v1/student")
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

//    @RequestMapping("/allStudents")
//    @GetMapping
//    public String allStudents(Model model){
//        List<Student> students =  studentService.getStudents();
//        model.addAttribute("students", students);
//        for (Student name: students){
//            System.out.println("Student name: "+name.getName());
//        }
//        return "studentsPage";
//    }

    @RequestMapping("/allStudents")
    @GetMapping
    public String allStudents(Model model){
        List<Student> students =  studentService.getStudents();
        model.addAttribute("students", students);
        model.addAttribute("studentForm", new Student());
        for (Student name: students){
            System.out.println("Student name: "+name.getName());
        }
        return "studentsPage";
    }

//    @PostMapping
//    public void registerNewStudent(@RequestBody Student student){
//        studentService.addNewStudent(student);
//    }

    @PostMapping("/register")
    public String registerNewStudent(@ModelAttribute Student student, Model model) throws IllegalArgumentException{
        try{
            System.out.println("Requested student: "+student);
            System.out.println("Requested students dob is: "+student.getDob());
            model.addAttribute("studentForm", new Student());
            model.addAttribute("standardDate", new Date());
            studentService.addNewStudent(student);
            System.out.println("New student "+student+" added!");
            model.addAttribute("message", "New Student successfully added!");
            return "studentsPage";
        }catch(IllegalArgumentException e){
            System.out.println("Parse attempt failed for value");
        }
        return "StudentPage";
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
        @PathVariable("studentId") Long studentId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email
    ){
        studentService.updateStudent(studentId, name, email);
    }

}
