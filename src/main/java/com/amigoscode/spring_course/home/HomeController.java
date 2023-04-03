package com.amigoscode.spring_course.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    @RequestMapping(("/home"))
    @GetMapping
    public String displayHomePage(){
        return "index.html";
    }

}
