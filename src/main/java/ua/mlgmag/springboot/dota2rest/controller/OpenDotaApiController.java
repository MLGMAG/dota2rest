package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

import java.util.Optional;

@Controller
@RequestMapping(UrlMappingConstants.OPEN_DOTA_API_CONTROLLER_REQUEST_MAPPING)
public class OpenDotaApiController {

    private final ApiService apiService;

    private final PlayerService playerService;

    @Autowired
    public OpenDotaApiController(ApiService apiService, PlayerService playerService) {
        this.apiService = apiService;
        this.playerService = playerService;
    }

    @PostMapping("/players")
    public String playerPost(@ModelAttribute("player") Player player, Model model) {
        Optional<Player> playerOptional = apiService.findPlayerById(player.getSteamId32());

        if (!playerOptional.isPresent()) {
            model.addAttribute("player", null);
            model.addAttribute("title", "Player not found");
            return "player";
        }

        Player playerPresent = playerOptional.get();

        playerPresent.setIsInDB(playerService.existById(playerPresent.getSteamId32()));
        model.addAttribute("player", playerPresent);
        model.addAttribute("title", "Player");
        return "Player/player";
    }

    @GetMapping("/players/{id}/peers")
    public String playerPeers(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Player> playerOptional = apiService.findPlayerById(id);

        if (!playerOptional.isPresent()) {
            model.addAttribute("peers", null);
            model.addAttribute("title", "Player not found");
            return "peers";
        }

        model.addAttribute("playerName", playerOptional.get().getName());
        model.addAttribute("peers", apiService.findAllPeersByPlayerId(id));
        model.addAttribute("title", "Pears");
        return "Player/peers";
    }
}
