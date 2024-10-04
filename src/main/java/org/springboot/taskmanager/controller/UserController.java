package org.springboot.taskmanager.controller;

import org.modelmapper.ModelMapper;
import org.springboot.taskmanager.exception.UserNotFoundException;
import org.springboot.taskmanager.requestbody.UserRequestBody;
import org.springboot.taskmanager.responsebody.UserResponseBody;
import org.springboot.taskmanager.security.UserMapper;
import org.springboot.taskmanager.service.TokenService;
import org.springboot.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController implements Controller<UserRequestBody, UserResponseBody> {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public UserController(@Autowired UserService userService,
                          @Autowired ModelMapper modelMapper,
                          @Autowired TokenService tokenService,
                          @Autowired UserMapper userMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }


    @PostMapping("")
    @Override
    public ResponseEntity<UserResponseBody> create(@RequestBody UserRequestBody requestBody) {
        var savedUser = userService.create(requestBody);
        var token = tokenService.generateToken(savedUser);
        var userResponse = userMapper.toUserResponseBody(savedUser, token);
        return ResponseEntity.accepted().body(userResponse);
    }


    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<UserResponseBody> update(@PathVariable Long id, UserRequestBody requestBody) {
        var savedUser = userService.update(id, requestBody);
        var userResponse = modelMapper.map(savedUser, UserResponseBody.class);
        return ResponseEntity.accepted().body(userResponse);
    }

    @DeleteMapping("delete")
    @Override
    public ResponseEntity<UserResponseBody> delete(@RequestParam Long id) {
         userService.delete(id);
         return ResponseEntity.noContent().build();
    }


    @GetMapping("getById")
    @Override
    public ResponseEntity<UserResponseBody> get(@RequestParam Long id) {
        var user = userService.get(id);
        var userResponse = modelMapper.map(user, UserResponseBody.class);
        return ResponseEntity.accepted().body(userResponse);
    }

    @GetMapping("getAll")
    @Override
    public ResponseEntity<List<UserResponseBody>> getAll() {
        var userList = userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserResponseBody.class))
                .collect(Collectors.toList());
        return ResponseEntity.accepted().body(userList);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
