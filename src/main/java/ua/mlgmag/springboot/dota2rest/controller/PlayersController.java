package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

import java.util.Optional;

@Controller
@RequestMapping(UrlMappingConstants.PLAYERS_CONTROLLER_REQUEST_MAPPING)
public class PlayersController {

    private final PlayerService playerService;

    private final ApiService apiService;

    @Autowired
    public PlayersController(PlayerService playerService, ApiService apiService) {
        this.playerService = playerService;
        this.apiService = apiService;
    }

    @GetMapping
    public String players(@RequestParam(value = "saveError", required = false) String saveError,
                          @RequestParam(value = "deleteError", required = false) String deleteError,
                          Model model) {
        model.addAttribute("players", playerService.findAll());
        model.addAttribute(new Player());
        model.addAttribute("deleteError", deleteError != null);
        model.addAttribute("saveError", saveError != null);
        model.addAttribute("title", "Saved players");
        return "players";
    }

    @GetMapping("/{id}")
    public String player(@PathVariable("id") Integer id, Model model) {
        Player player = playerService.findById(id).orElse(null);

        if (player == null) {
            model.addAttribute("title", "Object not found");
            return "player";
        }

        player.setIsInDB(playerService.existById(player.getSteamId32()));
        model.addAttribute("player", player);
        model.addAttribute("title", player.getName());
        return "player";
    }

    @GetMapping("/save")
    public String savePlayer(@RequestParam(value = "id") Integer id) {
        Player player = apiService.findPlayerById(id);

        if (player == null) {
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.PLAYERS_CONTROLLER_REQUEST_MAPPING + "?saveError";
        }

        playerService.save(player);
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.PLAYERS_CONTROLLER_REQUEST_MAPPING;
    }

    @GetMapping("/delete")
    public String deletePlayer(@RequestParam("id") Integer id) {
        Optional<Player> player = playerService.findById(id);

        if (!player.isPresent()) {
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.PLAYERS_CONTROLLER_REQUEST_MAPPING + "?deleteError";
        }

        playerService.delete(player.get());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.PLAYERS_CONTROLLER_REQUEST_MAPPING;
    }
}
