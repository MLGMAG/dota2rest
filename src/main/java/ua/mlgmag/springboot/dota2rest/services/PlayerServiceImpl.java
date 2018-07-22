package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void save(Player player) {
        log.info("save {}", player);
        playerRepository.save(player);
    }

    @Override
    public void delete(Player player) {
        log.info("delete {}", player);
        playerRepository.delete(player);
    }

    @Override
    public Optional<Player> findById(Integer id) {
        log.info("findById {}", id);
        return playerRepository.findById(id);
    }

    @Override
    public List<Player> findAll() {
        log.info("findAll");
        return playerRepository.findAll();
    }

    @Override
    public Boolean existById(Integer id) {
        return playerRepository.existsById(id);
    }
}
