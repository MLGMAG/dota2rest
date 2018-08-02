package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.OpenDotaApi.OpenDotaApiService;

@Controller
@RequestMapping(UrlMappingConstants.OPEN_DOTA_API_CONTROLLER_REQUEST_MAPPING)
public class OpenDotaApiController {

    private final OpenDotaApiService openDotaApiService;

    private final PlayerService playerService;

    @Autowired
    public OpenDotaApiController(OpenDotaApiService openDotaApiService, PlayerService playerService) {
        this.openDotaApiService = openDotaApiService;
        this.playerService = playerService;
    }

    @PostMapping("/players")
    public String playerPost(@ModelAttribute("player") Player modelPlayer,
                             @AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        try {
            Player player = openDotaApiService.findPlayerById(modelPlayer.getSteamId32());
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
    public String playerPeers(@PathVariable(value = "id") Integer id,
                              @AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        try {
            Player player = openDotaApiService.findPlayerById(id);
            model.addAttribute("playerName", player.getName());
            model.addAttribute("peers", openDotaApiService.findAllPeersByPlayerId(id));
            model.addAttribute("title", "Pears");
            return "Player/peers";
        } catch (IllegalStateException e) {
            model.addAttribute("peers", null);
            model.addAttribute("title", "Player not found");
            return "Player/peers";
        }
    }

    @GetMapping("/players/{id}/recentMatches")
    public String playerMatches(@PathVariable(value = "id") Integer id,
                                @AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        try {
            model.addAttribute("player", playerService.findById(id));
            model.addAttribute("matches", openDotaApiService.findRecentMatchesByPlayerId(id));
            model.addAttribute("title", "Recent matches");
            return "Player/matches";
        } catch (IllegalStateException e) {
            model.addAttribute("matchesNPE", "Error can't find matches");
            model.addAttribute("title", "Matches");
            return "Player/matches";
        }
    }

    @GetMapping("/heroes")
    public String heroes(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("heroesNPE", "Error can't find heroes");
        model.addAttribute("heroes", openDotaApiService.findAllHeroes());
        model.addAttribute("title", "GameMode");
        return "Player/heroes";
    }
}
