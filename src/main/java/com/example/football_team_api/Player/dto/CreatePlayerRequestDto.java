package com.example.football_team_api.Player.dto;


import com.example.football_team_api.Position.Position;
import jakarta.validation.constraints.*;

public class CreatePlayerRequestDto {
    @NotBlank(message = "Le nom du joueur est obligatoire")
    private String name;

    @NotNull(message = "La position est obligatoire")
    private Position position;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    @Override
    public String toString() {
        return "CreatePlayerRequestDto{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
