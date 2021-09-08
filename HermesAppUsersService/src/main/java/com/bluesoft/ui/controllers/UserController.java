package com.bluesoft.ui.controllers;

import com.bluesoft.services.UsersService;
import com.bluesoft.shared.UserDto;
import com.bluesoft.ui.model.CreateUserRequestModel;
import com.bluesoft.ui.model.CreateUserResponseModel;
import com.bluesoft.ui.model.LoginRequestModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return "Working on port "
                + environment.getProperty("local.server.port")
                + ", with token: " + environment.getProperty("token.secret");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        final UserDto createdUser = usersService.createUser(userDto);

        CreateUserResponseModel createUserResponseModel = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
    }

}
