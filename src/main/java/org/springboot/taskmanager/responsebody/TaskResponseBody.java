package org.springboot.taskmanager.responsebody;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TaskResponseBody {

    @NonNull
    private final long id;

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
}
