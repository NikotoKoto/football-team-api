package com.example.football_team_api.Player.service;
import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.dto.UpdatePlayerRequestDto;
import com.example.football_team_api.Player.entity.Player;
import com.example.football_team_api.Player.mapper.PlayerMapper;
import com.example.football_team_api.Player.repository.PlayerRepository;
import com.example.football_team_api.Team.entity.Team;
import com.example.football_team_api.Team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public PlayerResponseDto createPlayer( Long teamId,CreatePlayerRequestDto dto) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Team id not found"));
        Player player = PlayerMapper.toEntity(dto);
        player.setTeam(team);
        return PlayerMapper.toDto(playerRepository.save(player));
    }

    public List<PlayerResponseDto> getAllPlayers(Long teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Team id not found"));
        return playerRepository.findAll().stream()
                .map(PlayerMapper::toDto)
                .toList();
    }

    public PlayerResponseDto getPlayerById( Long teamId,Long playerId){
        Player player = playerRepository.findByIdAndTeamId(playerId, teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found in this team"));
        return PlayerMapper.toDto(player);

    }

    public PlayerResponseDto updatePlayer(Long teamId, Long playerId, UpdatePlayerRequestDto updateDto){
        Player player= playerRepository.findByIdAndTeamId(playerId,teamId)
                .orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Player not found in this team"));

        Player playerUpdated = PlayerMapper.toEntity(updateDto, player);
        return PlayerMapper.toDto(playerRepository.save(playerUpdated));
    }

    public void deletePlayer(Long teamId, Long playerId){
        Player player = playerRepository.findByIdAndTeamId(playerId,teamId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Player not found in this team"));
        playerRepository.delete(player);
    }
}
