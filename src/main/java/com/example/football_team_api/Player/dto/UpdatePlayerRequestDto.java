package com.example.football_team_api.Player.dto;

import com.example.football_team_api.Position.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class UpdatePlayerRequestDto {
    @NotBlank(message = "Le nom du joueur est obligatoire")
    private String name;

    @NotNull(message = "La position est obligatoire")
    private Position position;

}
