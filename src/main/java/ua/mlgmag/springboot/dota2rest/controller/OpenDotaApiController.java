package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
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

    @GetMapping(value = "proPlayers")
    public String proPlayers(Model model) {
        model.addAttribute("proPlayers", apiService.findAllProPlayers());
        return "proPlayers";
    }

    @GetMapping(value = "players")
    public String player(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("player", apiService.findPlayerById(id));
        return "player";
    }

    @GetMapping(value = "players/add")
    public String playerAdd(@RequestParam(value = "id") int id) {
        playerService.save(apiService.findPlayerById(id));
        return "redirect:/api/openDotaApi/proPlayers";
    }
}
