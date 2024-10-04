package org.springboot.taskmanager.requestbody;


import lombok.*;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserRequestBody {

    @NonNull
    private  String username;

    @NonNull
    private  String password;

}
