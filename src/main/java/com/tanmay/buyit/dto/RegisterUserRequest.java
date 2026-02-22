package com.tanmay.buyit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
