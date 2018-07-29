package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.PlayerServiceImpl;
import ua.mlgmag.springboot.dota2rest.services.User.UserServiceImpl;

@Controller
@RequestMapping(UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING)
public class UserController {

    private final UserServiceImpl userService;

    private final PlayerServiceImpl playerService;

    @Autowired
    public UserController(UserServiceImpl userService, PlayerServiceImpl playerService) {
        this.userService = userService;
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("title", currentUser.getUsername());
        model.addAttribute("user", userService.findByUsername(currentUser.getUsername()));
        model.addAttribute("players", userService.findByUsername(currentUser.getUsername()).getPlayerCollection());
        return "User/Profile";
    }

    @GetMapping("/collection/add")
    public String addToCollection(@RequestParam("id") Integer id,
                                  @AuthenticationPrincipal User currentUser) {
        userService.saveToCollection(playerService.findById(id), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING;
    }

    @GetMapping("/collection/delete")
    public String removeFromCollection(@RequestParam("id") Integer id,
                                       @AuthenticationPrincipal User currentUser) {
        userService.deleteFromCollection(playerService.findById(id), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.DATABASE_PLAYERS_CONTROLLER_REQUEST_MAPPING;
    }

}
