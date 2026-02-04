package com.example.smartNotes.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//This works, and this means that the embedded tomcat server works as I can run it on the localhost
@RestController //That was a bonus for me tbh, but I want to understand the difference between it and controller, is it only for Restful endpoints?
public class HealthController {
    //I am checking the
    @GetMapping("health") //Normal mapping for defining the mapping of the endpoint I believe, but not yet covered in the 2 sections I studied
    public String health(){
        return "This endpoint is to check that the app is running";
    }
}
