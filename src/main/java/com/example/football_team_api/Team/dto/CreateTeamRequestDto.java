package com.example.football_team_api.Team.dto;

import com.example.football_team_api.Player.dto.CreatePlayerRequestDto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class CreateTeamRequestDto {

    @NotBlank(message = "Le nom de l'équipe est obligatoire")
    private String name;

    @NotBlank(message = "L'acronyme est obligatoire")
    @Size(min = 2, max = 5, message = "L'acronyme doit contenir entre 2 et 5 lettres")
    private String acronym;

    @NotNull(message = "Le budget est obligatoire")
    @PositiveOrZero(message = "Le budget doit être positif")
    private BigDecimal budget;


    private List<CreatePlayerRequestDto> players;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAcronym() { return acronym; }
    public void setAcronym(String acronym) { this.acronym = acronym; }

    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }

    public List<CreatePlayerRequestDto> getPlayers() { return players; }
    public void setPlayers(List<CreatePlayerRequestDto> players) { this.players = players; }

    @Override
    public String toString() {
        return "CreateTeamRequestDto{" +
                "name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", budget=" + budget +
                ", players=" + players +
                '}';
    }
}