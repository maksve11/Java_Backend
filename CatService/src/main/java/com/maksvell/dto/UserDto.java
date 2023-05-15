package com.maksvell.dto;

import com.maksvell.entity.Owner;
import com.maksvell.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    @NotEmpty
    private Long id;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty
    private boolean active;

    @NotEmpty
    @NotBlank
    private Set<Role> roles;

    @NotBlank
    @NotNull
    private Owner owner;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}