package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/players")
    public String player(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("player", apiService.findPlayerById(id));
        return "player";
    }

    @GetMapping(value = "/players/{id}/peers")
    public String playerPeers(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("playerName", apiService.findPlayerById(id).getName());
        model.addAttribute("peers", apiService.findAllPeersById(id));
        return "peers";
    }
}
