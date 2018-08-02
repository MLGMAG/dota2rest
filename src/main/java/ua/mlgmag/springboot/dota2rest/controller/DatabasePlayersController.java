package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.definition.UserService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.OpenDotaApi.OpenDotaApiService;

import java.util.stream.Collectors;

@Controller
@RequestMapping(UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING)
public class DatabasePlayersController {

    private final PlayerService playerService;

    private final UserService userService;

    private final OpenDotaApiService openDotaApiService;

    @Autowired
    public DatabasePlayersController(PlayerService playerService, UserService userService, OpenDotaApiService openDotaApiService) {
        this.playerService = playerService;
        this.userService = userService;
        this.openDotaApiService = openDotaApiService;
    }

    @GetMapping
    public String players(@RequestParam(value = "saveError", required = false) String saveError,
                          @RequestParam(value = "deleteError", required = false) String deleteError,
                          @AuthenticationPrincipal User currentUser,
                          Model model) {
        model.addAttribute("userCollectionPlayersIds", userService.findByUsername(currentUser.getUsername()).getPlayerCollection()
                .stream().map(Player::getSteamId32).collect(Collectors.toSet()));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("players", playerService.findAll());
        model.addAttribute(new Player());
        model.addAttribute("deleteError", deleteError != null);
        model.addAttribute("saveError", saveError != null);
        model.addAttribute("title", "Players");
        return "Player/players";
    }

    @GetMapping("/{id}")
    public String player(@PathVariable("id") Integer id,
                         @AuthenticationPrincipal User currentUser, Model model) {
        Player player = playerService.findById(id);
        model.addAttribute("currentUser", currentUser);

        if (player == null) {
            model.addAttribute("title", "Object not found");
            return "Player/player";
        }

        player.setIsInDB(playerService.existInDatabaseById(player.getSteamId32()));
        model.addAttribute("player", player);
        model.addAttribute("title", player.getName());
        return "Player/player";
    }

    @GetMapping("/save")
    public String savePlayer(@RequestParam(value = "id") Integer id) {
        try {
            playerService.save(openDotaApiService.findPlayerById(id));
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING;
        } catch (IllegalStateException e) {
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING + "?saveError";
        }
    }

    @GetMapping("/delete")
    public String deletePlayer(@RequestParam("id") Integer id) {
        try {
            Player player = playerService.findById(id);
            if (!player.getUsers().isEmpty()) {
                return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING + "?deleteError";
            }
            playerService.delete(player);
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING;
        } catch (IllegalStateException e) {
            return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING + "?deleteError";
        }
    }
}