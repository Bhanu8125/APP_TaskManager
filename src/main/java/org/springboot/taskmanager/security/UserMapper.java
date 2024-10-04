package org.springboot.taskmanager.security;

import org.modelmapper.ModelMapper;
import org.springboot.taskmanager.model.Users;
import org.springboot.taskmanager.responsebody.UserResponseBody;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public UserResponseBody toUserResponseBody(Users user, String token) {
        // Map user entity fields to UserResponseBody
        UserResponseBody userResponseBody = modelMapper.map(user, UserResponseBody.class);

        // Set the JWT token manually
        userResponseBody.setToken(token);

        return userResponseBody;
    }
}

