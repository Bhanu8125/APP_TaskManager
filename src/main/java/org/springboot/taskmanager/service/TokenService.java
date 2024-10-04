package org.springboot.taskmanager.service;


import org.apache.catalina.User;
import org.springboot.taskmanager.model.Users;
import org.springboot.taskmanager.repository.UsersRepository;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public interface TokenService {
    String generateToken(Users users);
    String getUserNameFromToken(String token);
    boolean validateToken(String token, UsersRepository usersRepository);

}
