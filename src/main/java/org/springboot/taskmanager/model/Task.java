package org.springboot.taskmanager.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long  id;

    @NonNull
    private String  name;

    private String description;

    @NonNull
    private String  priority;

    @NonNull
    private String status;

    @NonNull
    private LocalDate dueDate;


    @NonNull
    private LocalDate createdDate;

    
    public Task(@NonNull String name, String description, @NonNull String priority, @NonNull String status, @NonNull LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdDate = LocalDate.now();
    }
}
