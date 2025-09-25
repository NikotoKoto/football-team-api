package com.example.football_team_api.Team.repository;
import com.example.football_team_api.Team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
