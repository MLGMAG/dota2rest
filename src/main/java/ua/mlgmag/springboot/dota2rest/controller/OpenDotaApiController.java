package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

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
    public String playerPost(@ModelAttribute("player") Player modelPlayer, Model model) {
        try {
            Player player = apiService.findPlayerById(modelPlayer.getSteamId32());
            player.setIsInDB(playerService.existInDatabaseById(player.getSteamId32()));
            model.addAttribute("player", player);
            model.addAttribute("title", "Player");
            return "Player/player";
        } catch (IllegalStateException | NullPointerException e) {
            model.addAttribute("player", null);
            model.addAttribute("title", "Player not found");
            return "Player/player";
        }
    }

    @GetMapping("/players/{id}/peers")
    public String playerPeers(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Player player = apiService.findPlayerById(id);
            model.addAttribute("playerName", player.getName());
            model.addAttribute("peers", apiService.findAllPeersByPlayerId(id));
            model.addAttribute("title", "Pears");
            return "Player/peers";
        } catch (IllegalStateException e) {
            model.addAttribute("peers", null);
            model.addAttribute("title", "Player not found");
            return "Player/peers";
        }
    }
}
