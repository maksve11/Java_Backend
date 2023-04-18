package dto.request;

import entities.Cat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateFleaDto {
    @NotNull(message="Name value can't be empty")
    private String name;
    @NotNull(message="Birthdate value can't be empty")
    private LocalDate birthdate;
    @NotNull
    private Cat cat;
}
