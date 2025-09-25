package com.example.football_team_api.Team.controller;

import com.example.football_team_api.Team.dto.CreateTeamRequestDto;
import com.example.football_team_api.Team.dto.TeamResponseDto;
import com.example.football_team_api.Team.dto.UpdateTeamRequestDto;
import com.example.football_team_api.Team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @Operation(summary = "Create a new team", description = "Add a team with required fields (name, acronym, budget). Players can be added later.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping
    public ResponseEntity<TeamResponseDto> createTeam(@RequestBody CreateTeamRequestDto dto){
        TeamResponseDto responseDto = teamService.createTeam(dto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Get all teams", description = "Retrieve a paginated list of teams, each containing their players. Supports sorting by name, acronym, or budget.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teams retrieved successfully")
    })
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

    @Operation(summary = "Get a team by ID", description = "Retrieve a single team with its players using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDto> getTeamByhId(@PathVariable Long id){
        TeamResponseDto responseDto = teamService.getTeamById(id);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Update a team", description = "Update the fields (name, acronym, budget) of a team by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team updated successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TeamResponseDto> UpdateTeamById(
            @PathVariable Long id,
            @RequestBody UpdateTeamRequestDto updateDto){
        TeamResponseDto responseDto = teamService.updateTeamById(id, updateDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Delete a team", description = "Remove a team from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Team deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}