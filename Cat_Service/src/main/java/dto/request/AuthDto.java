package dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 123456789;
    @NotBlank(message="Email is can't be empty")
    @Email(message="Please enter a valid email address")
    private String email;
    @NotBlank(message="Password is required")
    @Size(min = 8, max = 24, message="Password must be at least 8 characters and not more than 24 characters")
    private String password;
}
