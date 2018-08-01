package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.MatchService;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

@Controller
@RequestMapping(UrlMappingConstants.DATABASE_MATCHES_CONTROLLER_REQUEST_MAPPING)
public class DatabaseMatchesController {

    private final MatchService matchService;

    private final ApiService apiService;

    @Autowired
    public DatabaseMatchesController(MatchService matchService, ApiService apiService) {
        this.matchService = matchService;
        this.apiService = apiService;
    }

    @GetMapping
    public String matches(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("matches", matchService.findAll());
        model.addAttribute("matchesNPE", "Can't find any match in database");
        model.addAttribute("title", "Database matches");
        model.addAttribute("currentUser", currentUser);
        return "Player/matches";
    }

    @GetMapping("/saveMatches")
    public String saveMatches(@RequestParam("id") Integer playerId) {
        matchService.saveAllMatches(apiService.findMatchesByPlayerId(playerId), playerId);
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_MATCHES_CONTROLLER_REQUEST_MAPPING;
    }
}
