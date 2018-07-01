package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

@Controller
@RequestMapping("/api/openDotaApi")
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
        Player playerById = apiService.findPlayerById(player.getSteamId32());

        if (playerById == null) {
            model.addAttribute("player", null);
            model.addAttribute("title", "Player not found");
            return "player";
        }

        playerById.setIsInDB(playerService.existById(playerById.getSteamId32()));
        model.addAttribute("player", playerById);
        model.addAttribute("title", "Player");
        return "player";
    }

    @GetMapping("/players/{id}/peers")
    public String playerPeers(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("playerName", apiService.findPlayerById(id).getName());
        model.addAttribute("peers", apiService.findAllPeersByPlayerId(id));
        model.addAttribute("title", "Pears");
        return "peers";
    }
}
