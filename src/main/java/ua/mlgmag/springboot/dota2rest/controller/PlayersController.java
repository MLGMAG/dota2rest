package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

@Controller
@RequestMapping("/players")
public class PlayersController {

    private final PlayerService playerService;

    private final ApiService apiService;

    @Autowired
    public PlayersController(PlayerService playerService, ApiService apiService) {
        this.playerService = playerService;
        this.apiService = apiService;
    }

    @GetMapping("/saved")
    public String savedPlayers(Model model) {
        model.addAttribute("players", playerService.findAll());
        model.addAttribute(new Player());
        model.addAttribute("title", "Saved players");
        return "players";
    }

    @GetMapping("/{id}")
    public String player(@PathVariable("id") Integer id, Model model) {
        Player player = playerService.findById(id).orElse(null);
        player.setIsInDB(playerService.existById(player.getSteamId32()));
        model.addAttribute("player", player);
        model.addAttribute("title", player.getName());
        return "player";
    }

    @GetMapping("/save")
    public String savePlayer(@RequestParam(value = "id") Integer id) {
        playerService.save(apiService.findPlayerById(id));
        return "redirect:/players/saved";
    }

    @GetMapping("/delete")
    public String deletePlayer(@RequestParam("id") Integer id) {
        playerService.delete(playerService.findById(id).orElse(null));
        return "redirect:/players/saved";
    }
}
