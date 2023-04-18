package dto.response;

import entities.Flea;
import entities.Owner;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class CatResponseDto {
    protected Long id;
    private String name;
    private LocalDate birthdate;
    private String breed;
    private String color;
    private Owner owner;
    private List<Flea> fleas;
}
