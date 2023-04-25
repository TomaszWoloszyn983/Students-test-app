package com.amigoscode.spring_course.security;

import org.keycloak.adapters.KeycloakDeployment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoController {
    private KeycloakDeployment KeycloakSecurityContextUtils;

    @GetMapping("/student/logout")
    String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        System.out.println("\n\nYou have logged out");
        return "logout";
    }

    /**
     * This function doesn't seem to be working.
     *
     *
     * @return
     */
//    @GetMapping("/login")
//    public String login() {
//        return "redirect:" + getLoginUrl();
//    }
//
//    private String getLoginUrl() {
//        return KeycloakSecurityContextUtils.getAuthServerBaseUrl()
//                + "/realms/{realm}/protocol/openid-connect/auth"
//                + "?response_type=code"
//                + "&client_id={clientId}"
//                + "&redirect_uri={redirectUri}"
//                + "&state={state}"
//                + "&nonce={nonce}";
//    }
}
