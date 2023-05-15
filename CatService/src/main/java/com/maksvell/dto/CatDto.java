package com.maksvell.dto;

import com.maksvell.entity.Flea;
import com.maksvell.entity.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    private String breed;

    @NotNull
    private String color;

    @NotNull
    @NotEmpty
    private Owner owner;

    @NotNull
    private List<Flea> fleas;
}
