package com.example.football_team_api.Team.controller;

import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.dto.UpdateTeamRequestDto;
import com.example.football_team_api.Team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<TeamResponseDto>> getAllTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(teamService.getAllTeams(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDto> getTeamByhId(@PathVariable Long id){
        TeamResponseDto responseDto = teamService.getTeamById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamResponseDto> UpdateTeamById(@PathVariable Long id,@RequestBody UpdateTeamRequestDto updateDto){
        TeamResponseDto responseDto = teamService.updateTeamById(id, updateDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
