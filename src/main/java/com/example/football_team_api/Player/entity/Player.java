package com.example.football_team_api.Player.entity;

import com.example.football_team_api.Position.Position;
import com.example.football_team_api.Team.entity.Team;
import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}