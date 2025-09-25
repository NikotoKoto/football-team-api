package com.example.football_team_api.Player.repository;
import com.example.football_team_api.Player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long>{
        Optional<Player> findByIdAndTeamId(Long id, Long teamId);
        }
