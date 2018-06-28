package ua.mlgmag.springboot.dota2rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.mlgmag.springboot.dota2rest.model.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Integer> {
}
