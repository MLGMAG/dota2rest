package ua.mlgmag.springboot.dota2rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mlgmag.springboot.dota2rest.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
