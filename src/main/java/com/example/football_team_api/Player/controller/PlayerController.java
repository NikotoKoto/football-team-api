package com.example.football_team_api.Player.controller;

import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.dto.UpdatePlayerRequestDto;
import com.example.football_team_api.Player.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add a player", description = "Create a new player and assign him to a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @PostMapping
    public ResponseEntity<PlayerResponseDto> createPlayer(
            @PathVariable Long teamId,
            @RequestBody CreatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.createPlayer(teamId, dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Add several players", description = "Create multiple players and assign them to a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players created successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @PostMapping("/bulk")
    public ResponseEntity<List<PlayerResponseDto>> createPlayers(
            @PathVariable Long teamId,
            @RequestBody List<CreatePlayerRequestDto> playersDto) {
        List<PlayerResponseDto> responses = playersDto.stream()
                .map(dto -> playerService.createPlayer(teamId, dto))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get all players", description = "Retrieve all players of a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @GetMapping
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayers(@PathVariable Long teamId) {
        List<PlayerResponseDto> response = playerService.getAllPlayers(teamId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get player by ID", description = "Retrieve a specific player in a team by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Player or team not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> getPlayerById(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        PlayerResponseDto response = playerService.getPlayerById(teamId, id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update player", description = "Update a player's details in a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated successfully"),
            @ApiResponse(responseCode = "404", description = "Player or team not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> updatePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id,
            @RequestBody UpdatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.updatePlayer(teamId, id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete player", description = "Remove a player from a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Player deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Player or team not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        playerService.deletePlayer(teamId, id);
        return ResponseEntity.noContent().build();
    }
}