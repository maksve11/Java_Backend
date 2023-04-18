package dto.request;

import entities.Flea;
import entities.Owner;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCatDto {
    @NotNull(message="Name value can't be empty")
    private String name;
    @NotNull(message="Birthdate value can't be empty")
    private LocalDate birthdate;
    @NotNull(message="Breed value can't be empty")
    private String breed;
    @NotNull(message="Color value can't be empty")
    private String color;
    @NotNull
    private Owner owner;
    @NotNull
    private List<Flea> fleets;
}
