package com.library.auth.auth.dto;

import com.library.auth.auth.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterMember {
    @NotBlank
    @Size(max = 128)
    private String username;

    @NotBlank
    @ValidPassword
    private String password;

    @NotBlank
    @Size(max = 128)
    private String fullname;

    @NotBlank
    @Email
    @Size(max = 128)
    private String email;
}
