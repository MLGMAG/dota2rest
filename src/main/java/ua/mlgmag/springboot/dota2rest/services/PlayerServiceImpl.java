package ua.mlgmag.springboot.dota2rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void save(Player player) {
        if (!playerRepository.existsById(player.getSteamId32())) {
            playerRepository.save(player);
        }
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public Optional<Player> findById(Integer integer) {
        return playerRepository.findById(integer);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Boolean existById(Integer id) {
        return playerRepository.existsById(id);
    }
}