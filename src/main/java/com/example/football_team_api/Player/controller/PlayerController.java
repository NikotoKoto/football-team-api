package com.example.football_team_api.Player.controller;
import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.dto.UpdatePlayerRequestDto;
import com.example.football_team_api.Player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{teamId}/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Add a player into team
    @PostMapping
    public ResponseEntity<PlayerResponseDto> createPlayer(
            @PathVariable Long teamId,
            @RequestBody CreatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.createPlayer( teamId,dto);
        return ResponseEntity.status(201).body(response);
    }

    // Add several players into team
    @PostMapping("/bulk")
    public ResponseEntity<List<PlayerResponseDto>> createPlayers(
            @PathVariable Long teamId,
            @RequestBody List<CreatePlayerRequestDto> playersDto) {
        List<PlayerResponseDto> responses = playersDto.stream()
                .map(dto -> playerService.createPlayer(teamId, dto))
                .toList();
        return ResponseEntity.ok(responses);
    }

    // Get all players into team
    @GetMapping
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayers(@PathVariable Long teamId) {
        List<PlayerResponseDto> response = playerService.getAllPlayers(teamId);
        return ResponseEntity.ok(response);
    }

    // Get one player with his id
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> getPlayerById(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        PlayerResponseDto response = playerService.getPlayerById(teamId, id);
        return ResponseEntity.ok(response);
    }

    // Update player
    @PatchMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> updatePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id,
            @RequestBody  UpdatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.updatePlayer(teamId, id, dto);
        return ResponseEntity.ok(response);
    }

    // Delete player
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        playerService.deletePlayer(teamId, id);
        return ResponseEntity.noContent().build();
    }
}