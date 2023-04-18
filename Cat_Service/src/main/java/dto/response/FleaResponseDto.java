package dto.response;

import entities.Cat;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class FleaResponseDto {
    protected Long id;
    private String name;
    private LocalDate birthdate;
    private Cat cat;
}
