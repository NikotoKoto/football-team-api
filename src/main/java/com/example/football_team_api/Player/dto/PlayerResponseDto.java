package com.example.football_team_api.Player.dto;

import com.example.football_team_api.Position.Position;

public class PlayerResponseDto {
    private Long id;
    private String name;
    private Position position;

    public PlayerResponseDto(Long id, String name, Position position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }

    @Override
    public String toString() {
        return "PlayerResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';

}
}
