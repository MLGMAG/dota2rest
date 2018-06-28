package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

@Controller
@RequestMapping("/api/openDotaApi")
public class OpenDotaApiController {

    private final ApiService apiService;

    @Autowired
    public OpenDotaApiController(ApiService apiService) {
        this.apiService = apiService;
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
}
