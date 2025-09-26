package com.appexam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    @NotNull(message = "Le prénom ne doit pas être nul.")
    private String name;
    @NotNull(message = "Le nom d'utilisateur ne doit pas être nul.")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Le mot de passe ne doit pas être nul.")
    private String password;
    @NotNull(message = "Le rôle ne doit pas être nul.")
    private String role; // e.g., "USER", "MUNICIPAL_AGENT", "ADMIN"
    @NotNull(message = "La date d'inscription ne doit pas être nulle.")
    private LocalDate inscriptionDate;
}
