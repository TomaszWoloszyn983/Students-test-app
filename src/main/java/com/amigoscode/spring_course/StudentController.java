package com.amigoscode.spring_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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


    @RequestMapping("/allStudents")
    @GetMapping
    public String allStudents(Model model){
        List<Student> students =  studentService.getStudents();
        model.addAttribute("students", students);
        model.addAttribute("studentForm", new Student());
        return "studentsPage";
    }


    @PostMapping("/register")
    public String registerNewStudent(@ModelAttribute Student student, Model model) throws IllegalArgumentException{
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

    @PostMapping("/updateStudent/{studentId}/submitChanges")
    public String submitChanges(
            @PathVariable(value = "studentId") Long studentId,
            @ModelAttribute("student") Student student){

        Student studentToUpdate = studentService.findStudentById(studentId);
        studentService.updateStudent(studentId, student.getName(), student.getEmail(), student.getDob());

        return "redirect:/student/allStudents";
    }

    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        this.studentService.deleteStudent(studentId);
        return "redirect:/student/allStudents";
    }

    @GetMapping(path = "/updateStudent/{studentId}")
    public String updateStudent(@PathVariable(value = "studentId")
                                Long studentId, Model model){

            Student studentToUpdate = studentService.findStudentById(studentId);
            model.addAttribute("student", studentToUpdate);

    return "updateStudent";
    }

}
