package dto.response;

import entities.Cat;
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
public class OwnerResponseDto {
    protected Long id;
    private String name;
    private LocalDate birthdate;
    private List<Cat> cats;
}
