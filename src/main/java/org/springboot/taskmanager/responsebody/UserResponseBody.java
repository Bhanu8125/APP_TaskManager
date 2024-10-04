package org.springboot.taskmanager.responsebody;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserResponseBody {

    @NonNull
    private Integer id;

    @NonNull
    private  String username;

    private String token;
}
