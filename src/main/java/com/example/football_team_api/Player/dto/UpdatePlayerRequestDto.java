package com.example.football_team_api.Player.dto;

import com.example.football_team_api.Position.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class UpdatePlayerRequestDto {
    @NotBlank(message = "Le nom du joueur est obligatoire")
    private String name;

    @NotNull(message = "La position est obligatoire")
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
