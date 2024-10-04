package org.springboot.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private  String password;


    public Users() {
    }

    public Users(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
