package com.example.football_team_api.Team.dto;

import com.example.football_team_api.Player.dto.PlayerResponseDto;

import java.math.BigDecimal;
import java.util.List;

public class TeamResponseDto {
    private Long id;
    private String name;
    private String acronym;
    private BigDecimal budget;
    private List<PlayerResponseDto> players;

    public TeamResponseDto(Long id, String name, String acronym, BigDecimal budget, List<PlayerResponseDto> players) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.budget = budget;
        this.players = players;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAcronym() { return acronym; }
    public BigDecimal getBudget() { return budget; }
    public List<PlayerResponseDto> getPlayers() { return players; }

    @Override
    public String toString() {
        return "TeamResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", budget=" + budget +
                ", players=" + players +
                '}';
    }
}
