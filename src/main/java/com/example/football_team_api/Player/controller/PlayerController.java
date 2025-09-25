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

    // Créer un joueur dans une équipe
    @PostMapping
    public ResponseEntity<PlayerResponseDto> createPlayer(
            @PathVariable Long teamId,
            @RequestBody CreatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.createPlayer( teamId,dto);
        return ResponseEntity.status(201).body(response);
    }

    // Récupérer tous les joueurs d’une équipe
    @GetMapping
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayers(@PathVariable Long teamId) {
        List<PlayerResponseDto> response = playerService.getAllPlayers(teamId);
        return ResponseEntity.ok(response);
    }

    // Récupérer un joueur par son id
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> getPlayerById(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        PlayerResponseDto response = playerService.getPlayerById(teamId, id);
        return ResponseEntity.ok(response);
    }

    // Mettre à jour un joueur
    @PatchMapping("/{id}")
    public ResponseEntity<PlayerResponseDto> updatePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id,
            @RequestBody  UpdatePlayerRequestDto dto) {

        PlayerResponseDto response = playerService.updatePlayer(teamId, id, dto);
        return ResponseEntity.ok(response);
    }

    // Supprimer un joueur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Long teamId,
            @PathVariable Long id) {

        playerService.deletePlayer(teamId, id);
        return ResponseEntity.noContent().build();
    }
}