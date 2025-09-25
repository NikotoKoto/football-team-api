package com.example.football_team_api.players.service;

import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.dto.UpdatePlayerRequestDto;
import com.example.football_team_api.Player.entity.Player;
import com.example.football_team_api.Player.mapper.PlayerMapper;
import com.example.football_team_api.Player.repository.PlayerRepository;
import com.example.football_team_api.Player.service.PlayerService;
import com.example.football_team_api.Position.Position;
import com.example.football_team_api.Team.entity.Team;
import com.example.football_team_api.Team.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerService playerService;

    /**
     * Verify that creating a player:
     *  - attaches the player to the correct team
     *  - persists the player
     *  - returns the expected DTO
     */
    @Test
    void should_createPlayer_persists_and_returnsDto() {
        Team team = new Team(); team.setId(1L); team.setName("PSG");

        CreatePlayerRequestDto dto = new CreatePlayerRequestDto();
        dto.setName("Mbappé");
        dto.setPosition(Position.FW);

        Player saved = new Player();
        saved.setId(10L);
        saved.setName("Mbappé");
        saved.setPosition(Position.FW);
        saved.setTeam(team);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(playerRepository.save(any(Player.class))).thenReturn(saved);

        PlayerResponseDto out = playerService.createPlayer(1L, dto);

        assertThat(out.getId()).isEqualTo(10L);
        assertThat(out.getName()).isEqualTo("Mbappé");
        assertThat(out.getPosition()).isEqualTo(Position.FW);

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());
        assertThat(captor.getValue().getTeam().getId()).isEqualTo(1L);
    }

    /**
     * Verify that retrieving all players:
     *  - returns a list of player DTOs
     */
    @Test
    void should_getAllPlayersByTeam_returnsDtos() {
        // Arrange
        Team team = new Team();
        team.setId(1L);

        Player p1 = new Player();
        p1.setId(1L);
        p1.setName("Bulka");
        p1.setPosition(Position.GK);
        p1.setTeam(team);

        Player p2 = new Player();
        p2.setId(2L);
        p2.setName("Todibo");
        p2.setPosition(Position.DF);
        p2.setTeam(team);

        team.setPlayers(List.of(p1, p2));

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        List<PlayerResponseDto> result = playerService.getAllPlayers(1L);
//Assert
        assertThat(result).hasSize(2);

        verify(teamRepository).findById(1L);
        verifyNoInteractions(playerRepository);
    }
    /**
     * Verify that retrieving a player by ID:
     *  - returns the correct DTO when the player belongs to the given team
     */
    @Test
    void should_getPlayerById_returnsDto() {
        Team team = new Team(); team.setId(1L);

        Player p = new Player();
        p.setId(5L);
        p.setName("Dante");
        p.setPosition(Position.DF);
        p.setTeam(team);

        when(playerRepository.findByIdAndTeamId(5L, 1L)).thenReturn(Optional.of(p));

        PlayerResponseDto out = playerService.getPlayerById(1L, 5L);

        assertThat(out.getId()).isEqualTo(5L);
        assertThat(out.getName()).isEqualTo("Dante");
        assertThat(out.getPosition()).isEqualTo(Position.DF);
    }

    /**
     * Verify that updating a player:
     *  - applies new values from the DTO
     *  - persists the updated player
     */
    @Test
    void should_updatePlayer() {
        Team team = new Team(); team.setId(1L);

        Player existing = new Player();
        existing.setId(3L);
        existing.setName("OLD");
        existing.setPosition(Position.DF);
        existing.setTeam(team);

        when(playerRepository.findByIdAndTeamId(3L, 1L)).thenReturn(Optional.of(existing));
        when(playerRepository.save(any(Player.class))).thenAnswer(i -> i.getArgument(0));

        UpdatePlayerRequestDto dto = new UpdatePlayerRequestDto();
        dto.setName("NEW");
        dto.setPosition(Position.MF);

        PlayerResponseDto out = playerService.updatePlayer(1L, 3L, dto);

        assertThat(out.getName()).isEqualTo("NEW");
        assertThat(out.getPosition()).isEqualTo(Position.MF);

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());
        assertThat(captor.getValue().getTeam().getId()).isEqualTo(1L);
    }

    /**
     * Verify that deleting a player:
     *  - removes the player from the repository when it exists
     */
    @Test
    void should_deletePlayer() {
        Team team = new Team(); team.setId(1L);
        Player p = new Player(); p.setId(7L); p.setTeam(team);

        when(playerRepository.findByIdAndTeamId(7L, 1L)).thenReturn(Optional.of(p));

        playerService.deletePlayer(1L, 7L);

        verify(playerRepository).delete(p);
    }

    /**
     * Verify that requesting a non-existing player:
     *  - throws a ResponseStatusException with NOT_FOUND
     */
    @Test
    void should_getPlayerById_throws_whenNotFound() {
        when(playerRepository.findByIdAndTeamId(99L, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.getPlayerById(1L, 99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Player not found in this team");
    }
}