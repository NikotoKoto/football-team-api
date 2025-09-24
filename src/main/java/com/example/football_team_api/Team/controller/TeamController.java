package com.example.football_team_api.Team.controller;

import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.service.TeamService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<TeamResponseDto> createTeam(@RequestBody CreateTeamRequestDto dto){
        TeamResponseDto responseDto = teamService.createTeam(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDto>> getAllTeam(){
        List<TeamResponseDto> responseDto = teamService.getAllTeam();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/id")
    public ResponseEntity<TeamResponseDto> getTeamWithId(@PathVariable Long id){
        TeamResponseDto responseDto = teamService.getTeamWithId();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/id")
    public ResponseEntity<TeamResponseDto> UpdateTeamWithId(@PathVariable Long id, UpdateTeamRequestDto updateDto){
        TeamResponseDto responseDto = teamService.updateTeamWithId(updateDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
