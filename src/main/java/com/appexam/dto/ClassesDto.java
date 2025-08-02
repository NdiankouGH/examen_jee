package com.appexam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDto {

    private Long id;
    @NotNull(message = "Le nom de classe ne doit pas être nul.")
    private String className;
    @NotNull(message = "La description de la classe ne doit pas être nulle.")
    private String description;
    private int sectorId;
}
