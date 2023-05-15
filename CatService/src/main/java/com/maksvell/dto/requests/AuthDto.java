package com.maksvell.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 123456789;

    @NotBlank(message = "Username is can't be empty")
    @NotNull
    private String username;

    @NotBlank(message="Email is can't be empty")
    @Email(message="Please enter a valid email address")
    @NotNull
    private String email;

    @NotBlank(message="Password is required")
    @Size(min = 8, max = 24, message="Password must be at least 8 characters and not more than 24 characters")
    private String password;
}
