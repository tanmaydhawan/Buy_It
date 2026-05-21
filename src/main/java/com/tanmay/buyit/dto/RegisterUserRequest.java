package com.tanmay.buyit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUserRequest {

    @NotBlank(message = "First Name can't be Null!")
    @Size(min = 3)
    @Schema(description = "First Name", example = "Khush")
    private String firstName;

    @NotBlank(message = "Last Name can't be Null!")
    @Size(min = 3)
    @Schema(description = "Last Name", example = "Dhawan")
    private String lastName;

    @Email(message = "Use Proper format for email!")
    @NotBlank(message = "Email Name can't be Null!")
    @Schema(description = "Email Id", example = "kd@gmail.com")
    private String email;

    @NotBlank(message = "Password Name can't be Null!")
    @Size(min = 8, message = "Password must be strong i.e. more than 8 characters")
    @Schema(description = "password", example = "*****")
    private String password;
}
