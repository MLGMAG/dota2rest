package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;

@Controller
@RequestMapping(value = "/players")
public class PlayersController {

    private final PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/saved")
    public String savedPlayers(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "players";
    }

    @GetMapping
    public String player(@RequestParam(value = "id") Integer id, Model model) {
        model.addAttribute("player", playerService.findById(id).orElse(null));
        return "player";
    }
}
