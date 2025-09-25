package com.example.football_team_api.Team.mapper;
import com.example.football_team_api.Player.dto.PlayerResponseDto;
import com.example.football_team_api.Player.entity.Player;
import com.example.football_team_api.Player.mapper.PlayerMapper;
import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.dto.UpdateTeamRequestDto;
import com.example.football_team_api.Team.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {

    public static Team toEntity(CreateTeamRequestDto dto){
        Team team = new Team();
        team.setName(dto.getName());
        team.setAcronym(dto.getAcronym());
        team.setBudget(dto.getBudget());

        if(dto.getPlayers() != null){
            List<Player> players = dto.getPlayers().stream()
                    .map(PlayerMapper::toEntity)
                    .collect(Collectors.toList());
            players.forEach(p-> p.setTeam(team));
            team.setPlayers(players);
        }
        return team;
    }

    public static void updateEntityFromDto(UpdateTeamRequestDto dto, Team target) {
        if (dto.getName() != null) {
            String name = dto.getName().trim();
            if (!name.isEmpty()) target.setName(name);
        }
        if (dto.getAcronym() != null) {
            String ac = dto.getAcronym().trim();
            if (!ac.isEmpty()) target.setAcronym(ac);
        }
        if (dto.getBudget() != null) {
            target.setBudget(dto.getBudget());
        }
    }

    public static TeamResponseDto toDto(Team team) {
        List<PlayerResponseDto> players = null;
        if (team.getPlayers() != null) {
            players = team.getPlayers().stream()
                    .map(PlayerMapper::toDto)
                    .collect(Collectors.toList());
        }

        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getAcronym(),
                team.getBudget(),
                players
        );
    }

}
