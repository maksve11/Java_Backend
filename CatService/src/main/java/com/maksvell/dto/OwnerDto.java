package com.maksvell.dto;

import com.maksvell.entity.Cat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    private List<Cat> cats;
}
