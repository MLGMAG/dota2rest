package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.SecurityService;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.PlayerServiceImpl;
import ua.mlgmag.springboot.dota2rest.services.User.UserServiceImpl;

@Controller
@RequestMapping(UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING)
public class UserController {

    private final UserServiceImpl userService;

    private final PlayerServiceImpl playerService;

    private final SecurityService securityService;

    @Autowired
    public UserController(UserServiceImpl userService, PlayerServiceImpl playerService, SecurityService securityService) {
        this.userService = userService;
        this.playerService = playerService;
        this.securityService = securityService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("title", currentUser.getUsername());
        model.addAttribute("players", userService.findByUsername(securityService.findLoggedInUsername()).getPlayerCollection());
        return "User/Profile";
    }

    @GetMapping("/collection/add")
    public String addToCollection(@RequestParam("id") Integer id,
                                  @AuthenticationPrincipal User currentUser) {
        userService.saveToCollection(playerService.findById(id).orElse(null), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING + "/profile";
    }

    @GetMapping("/collection/delete")
    public String removeFromCollection(@RequestParam("id") Integer id,
                                       @AuthenticationPrincipal User currentUser) {
        userService.deleteFromCollection(playerService.findById(id).orElse(null), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING + "/profile";
    }

}
