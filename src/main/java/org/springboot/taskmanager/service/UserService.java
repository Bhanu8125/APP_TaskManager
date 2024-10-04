package org.springboot.taskmanager.service;

import org.springboot.taskmanager.exception.UserNotFoundException;
import org.springboot.taskmanager.model.Users;
import org.springboot.taskmanager.repository.UsersRepository;
import org.springboot.taskmanager.requestbody.UserRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public  class UserService implements Services<UserRequestBody, Users>{

    private final UsersRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(@Autowired UsersRepository userRepository,
                       @Autowired PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Users create(UserRequestBody requestbody) {
        var encodedPassword = passwordEncoder.encode(requestbody.getPassword());
        var savedUser = userRepository.save(new Users(requestbody.getUsername(), encodedPassword));

        //var savedUser = userRepository.save(new Users(requestbody.getUsername(), requestbody.getPassword()));
        return savedUser;
    }



    @Override
    public Users update(Long id, UserRequestBody requestbody) {
        return userRepository.findById(id).map((user) -> {
                    var encodedPassword = passwordEncoder.encode(requestbody.getPassword());
                    user.setPassword(encodedPassword);
                    //user.setPassword(requestbody.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void delete(Long id)  {
        userRepository.delete(get(id));
    }

    @Override
    public Users get(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        else{
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public List<Users> getAll() {
        return userRepository.findAll();
    }
}
