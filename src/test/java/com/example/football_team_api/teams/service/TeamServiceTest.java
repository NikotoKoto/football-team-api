package com.example.football_team_api.teams.service;

import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.dto.UpdateTeamRequestDto;
import com.example.football_team_api.Team.entity.Team;
import com.example.football_team_api.Team.repository.TeamRepository;
import com.example.football_team_api.Team.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    /**
     * Verify that creating a team:
     *  - persists the entity through the repository
     *  - returns a DTO with the correct values
     */
    @Test
    void should_createTeam_persists_and_returnsDto() {
        // Arrange
        CreateTeamRequestDto req = new CreateTeamRequestDto();
        req.setName("PSG");
        req.setAcronym("PSG");
        req.setBudget(new BigDecimal("500000000"));

        Team saved = new Team();
        saved.setId(1L);
        saved.setName("PSG");
        saved.setAcronym("PSG");
        saved.setBudget(new BigDecimal("500000000"));

        when(teamRepository.save(any(Team.class))).thenReturn(saved);

        // Act
        TeamResponseDto out = teamService.createTeam(req);

        // Assert
        assertThat(out.getId()).isEqualTo(1L);
        assertThat(out.getName()).isEqualTo("PSG");
        assertThat(out.getAcronym()).isEqualTo("PSG");
        assertThat(out.getBudget()).isEqualByComparingTo("500000000");

        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("PSG");
    }

    /**
     * Verify that retrieving all teams with pagination:
     *  - returns the correct number of results
     *  - applies sorting by name as requested
     */
    @Test
    void should_getAllTeams_returnsPagedDtos() {
        Team t1 = new Team(); t1.setId(1L); t1.setName("A"); t1.setAcronym("A"); t1.setBudget(new BigDecimal("1"));
        Team t2 = new Team(); t2.setId(2L); t2.setName("B"); t2.setAcronym("B"); t2.setBudget(new BigDecimal("2"));

        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
        Page<Team> page = new PageImpl<>(List.of(t1, t2), pageable, 2);

        when(teamRepository.findAll(pageable)).thenReturn(page);

        Page<TeamResponseDto> result = teamService.getAllTeams(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("A");
        verify(teamRepository).findAll(pageable);
    }

    /**
     * Verify that retrieving a team by ID:
     *  - returns the corresponding DTO when the team exists
     */
    @Test
    void should_getTeamById_returnsDto() {
        Team t = new Team();
        t.setId(42L);
        t.setName("OGC Nice");
        t.setAcronym("OGCN");
        t.setBudget(new BigDecimal("100000000"));

        when(teamRepository.findById(42L)).thenReturn(Optional.of(t));

        TeamResponseDto out = teamService.getTeamById(42L);

        assertThat(out.getId()).isEqualTo(42L);
        assertThat(out.getName()).isEqualTo("OGC Nice");
    }

    /**
     * Verify that updating a team:
     *  - updates only the provided fields
     *  - keeps the existing values for fields not provided
     */
    @Test
    void should_updateTeamById_updatesFields() {
        Team existing = new Team();
        existing.setId(1L);
        existing.setName("OLD");
        existing.setAcronym("OLD");
        existing.setBudget(new BigDecimal("100"));

        when(teamRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArgument(0));

        UpdateTeamRequestDto dto = new UpdateTeamRequestDto();
        dto.setName("NEW");
        dto.setBudget(new BigDecimal("200"));

        TeamResponseDto out = teamService.updateTeamById(1L, dto);

        assertThat(out.getName()).isEqualTo("NEW");
        assertThat(out.getBudget()).isEqualByComparingTo("200");

        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(captor.capture());
        assertThat(captor.getValue().getAcronym()).isEqualTo("OLD");
    }

    /**
     * Verify that deleting a team:
     *  - calls repository.delete() when the team exists
     */
    @Test
    void should_deleteTeamById_deletesWhenExists() {
        Team t = new Team(); t.setId(9L);
        when(teamRepository.findById(9L)).thenReturn(Optional.of(t));

        teamService.deleteTeam(9L);

        verify(teamRepository).delete(t);
    }
}