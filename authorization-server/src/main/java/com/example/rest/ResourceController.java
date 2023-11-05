package com.example.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourceController {

    @GetMapping("/home")
    public String home() {
        return "You have been got 'Home' page";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(Principal principal) {
        return "Dear '" + principal.getName() + "', you have been authorized and got 'User' resource";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String manager(Principal principal) {
        return "Dear '" + principal.getName() + "', you have been authorized and got 'Manager' resource";
    }
}
