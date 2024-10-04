package org.springboot.taskmanager.requestbody;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskRequestBody {

    @NonNull
    private String name;

    private String description;

    @NonNull
    private String priority;

    @NonNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;

    @NonNull
    private String status;
}
