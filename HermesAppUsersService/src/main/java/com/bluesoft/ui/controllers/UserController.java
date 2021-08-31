package com.bluesoft.ui.controllers;

import com.bluesoft.services.UsersService;
import com.bluesoft.shared.UserDto;
import com.bluesoft.ui.model.CreateUserRequestModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public  class UserController {


    private final Environment environment;
    private final UsersService usersService;

    public UserController(final Environment environment, final UsersService usersService) {
        this.environment = environment;
        this.usersService = usersService;
    }

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        usersService.createUser(userDto);

        return "Create user method is called";
    }

}
