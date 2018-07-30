package ua.mlgmag.springboot.dota2rest.controller;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mlgmag.springboot.dota2rest.config.security.SecurityConfig;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.enums.Authority;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.services.PlayerServiceImpl;
import ua.mlgmag.springboot.dota2rest.services.User.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING)
public class UserController {

    private final UserServiceImpl userService;

    private final PlayerServiceImpl playerService;

    private final SecurityConfig securityConfig;

    @Autowired
    public UserController(UserServiceImpl userService, PlayerServiceImpl playerService, SecurityConfig securityConfig) {
        this.userService = userService;
        this.playerService = playerService;
        this.securityConfig = securityConfig;
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "updateSuccess", required = false) String saveSuccess,
                          @RequestParam("tab") String tab,
                          @AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("saveSuccess", saveSuccess != null);
        model.addAttribute("tab", tab);
        model.addAttribute("title", currentUser.getUsername());
        model.addAttribute("currentUser", userService.findByUsername(currentUser.getUsername()));
        model.addAttribute("updateUser", currentUser);
        model.addAttribute("authorities", ImmutableSet.of(Authority.USER, Authority.ADMIN));
        model.addAttribute("players", userService.findByUsername(currentUser.getUsername()).getPlayerCollection());
        return "User/Profile";
    }

    @GetMapping("/collection/add")
    public String addToCollection(@RequestParam("id") Integer id,
                                  @AuthenticationPrincipal User currentUser) {
        userService.saveToCollection(playerService.findById(id), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING + "/profile?tab=collection-tab";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("newUser") User updatedUser,
                         HttpServletRequest request, HttpServletResponse response,
                         @AuthenticationPrincipal User currentUser, Model model) {

        if (userService.updateValidation(updatedUser, model)) {
            updatedUser.setPlayerCollection(currentUser.getPlayerCollection());
            model.addAttribute("tab", "edit-tab");
            model.addAttribute("title", currentUser.getUsername());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("updateUser", updatedUser);
            model.addAttribute("authorities", ImmutableSet.of(Authority.USER, Authority.ADMIN));
            model.addAttribute("players", userService.findByUsername(currentUser.getUsername()).getPlayerCollection());
            return "User/Profile";
        }

        updatedUser.setPassword(securityConfig.getPasswordEncoder().encode(updatedUser.getPassword()));
        updatedUser.setPlayerCollection(currentUser.getPlayerCollection());
        userService.update(updatedUser);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return UrlMappingConstants.REDIRECT + "signIn?updateSuccess";
    }

    @GetMapping("/collection/delete")
    public String removeFromCollection(@RequestParam("id") Integer id,
                                       @AuthenticationPrincipal User currentUser) {
        userService.deleteFromCollection(playerService.findById(id), currentUser.getUsername());
        return UrlMappingConstants.REDIRECT + UrlMappingConstants.USER_CONTROLLER_REQUEST_MAPPING + "/profile?tab=collection-tab";
    }

}
