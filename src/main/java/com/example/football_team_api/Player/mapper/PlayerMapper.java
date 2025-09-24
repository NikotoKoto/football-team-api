package com.example.football_team_api.Player.mapper;

import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.entity.Player;
import com.example.football_team_api.Position.Position;

public class PlayerMapper {
    public static Player toEntity(CreatePlayerRequestDto dto) {
        Player player = new Player();
        player.setName(dto.getName());
        player.setPosition(dto.getPosition());
        return player;
    }

    public static PlayerResponseDto toDto(Player player) {
        return new PlayerResponseDto(
                player.getId(),
                player.getName(),
                player.getPosition()
        );

}
}
