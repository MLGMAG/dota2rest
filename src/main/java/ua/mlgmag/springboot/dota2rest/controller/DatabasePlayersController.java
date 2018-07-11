package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING)
public class DatabasePlayersController {

    private final PlayerService playerService;

    private final ApiService apiService;

    @Autowired
    public DatabasePlayersController(PlayerService playerService, ApiService apiService) {
        this.playerService = playerService;
        this.apiService = apiService;
    }

    @GetMapping
    public List<Player> players() {
        return playerService.findAll();
    }

    @GetMapping("/{id}")
    public Player player(@PathVariable("id") Integer id) {
        return playerService.findById(id).orElse(null);
    }

    @GetMapping("/save")
    public Player savePlayer(@RequestParam(value = "id") Integer id) {
        Optional<Player> playerOptional = apiService.findPlayerById(id);

        if (!playerOptional.isPresent()) {
            return null;
        }

        playerService.save(playerOptional.get());
        return playerOptional.get();
    }

    @GetMapping("/delete")
    public Player deletePlayer(@RequestParam("id") Integer id) {
        Optional<Player> player = playerService.findById(id);

        if (!player.isPresent()) {
            return null;
        }

        playerService.delete(player.get());
        return player.get();
    }
}
