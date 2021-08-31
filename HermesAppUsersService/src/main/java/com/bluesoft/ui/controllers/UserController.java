package com.bluesoft.ui.controllers;

import com.bluesoft.ui.model.CreateUserRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public  class UserController {

    @Autowired
    private Environment environment;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
        return "Create user method is called";
    }

}
