package com.example.football_team_api.Team.service;


import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.entity.Team;
import com.example.football_team_api.Team.mapper.TeamMapper;
import com.example.football_team_api.Team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public TeamResponseDto createOrder(CreateTeamRequestDto dto){
        Team team = TeamMapper.toEntity(dto);
        return TeamMapper.toDto(team);
    }
}
