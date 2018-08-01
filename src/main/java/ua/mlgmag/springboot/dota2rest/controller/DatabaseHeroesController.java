package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.HeroService;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.ApiService;

@Controller
@RequestMapping(UrlMappingConstants.DATABASE_HEROES_CONTROLLER_REQUEST_MAPPING)
public class DatabaseHeroesController {

    private final HeroService heroService;

    private final ApiService apiService;

    @Autowired
    public DatabaseHeroesController(HeroService heroService, ApiService apiService) {
        this.heroService = heroService;
        this.apiService = apiService;
    }

    @GetMapping
    public String heroes(@RequestParam(value = "updateSuccess", required = false) String updateSuccess,
                         @AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("updateSuccess", updateSuccess != null);
        model.addAttribute("heroes", heroService.findAll());
        model.addAttribute("heroesNPE", "Can't find any hero in database");
        model.addAttribute("title", "Database heroes");
        model.addAttribute("currentUser", currentUser);
        return "Player/heroes";
    }

    @GetMapping("/updateHeroes")
    public String updateHeroes() {
        heroService.saveAllHeroes(apiService.findAllHeroes());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_HEROES_CONTROLLER_REQUEST_MAPPING + "?updateSuccess";
    }
}
