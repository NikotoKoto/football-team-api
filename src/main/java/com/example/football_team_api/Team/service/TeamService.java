package com.example.football_team_api.Team.service;


import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.dto.UpdateTeamRequestDto;
import com.example.football_team_api.Team.entity.Team;
import com.example.football_team_api.Team.mapper.TeamMapper;
import com.example.football_team_api.Team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public TeamResponseDto createTeam(CreateTeamRequestDto dto){
        Team team = TeamMapper.toEntity(dto);
        return TeamMapper.toDto(teamRepository.save(team));
    }

    public List<TeamResponseDto> getAllTeam(){

        return teamRepository.findAll().stream()
                .map(TeamMapper::toDto)
                .toList();
    }

    public TeamResponseDto getTeamById(Long id){
        Team team = teamRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        return TeamMapper.toDto(team);
    }

    public TeamResponseDto updateTeamWithId(Long id, UpdateTeamRequestDto dto){
        Team team = teamRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        Team updated = TeamMapper.toEntity(dto,team);
        return TeamMapper.toDto(teamRepository.save(updated));
    }

    public void deleteTeam(Long id){
        Team team = teamRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        teamRepository.delete(team);
    }
}
