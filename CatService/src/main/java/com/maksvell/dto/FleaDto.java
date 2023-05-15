package com.maksvell.dto;

import com.maksvell.entity.Cat;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FleaDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Cat cat;
}
