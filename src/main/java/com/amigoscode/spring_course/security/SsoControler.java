package com.amigoscode.spring_course.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;

@RestController
public class SsoControler {
    @GetMapping("/logout")
    void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }
}
