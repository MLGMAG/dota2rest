package ua.mlgmag.springboot.dota2rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ua.mlgmag.springboot.dota2rest.config.security.SecurityConfig;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.UserService;
import ua.mlgmag.springboot.dota2rest.dto.CaptchaResponseDto;
import ua.mlgmag.springboot.dota2rest.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Controller
public class SecurityController {

    private final UserService userService;

    private final SecurityConfig securityConfig;

    private final RestTemplate restTemplate;

    @Value("${captcha.secret}")
    private String captchaSecret;

    @Autowired
    public SecurityController(UserService userService, SecurityConfig securityConfig, RestTemplate restTemplate) {
        this.userService = userService;
        this.securityConfig = securityConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/signUp")
    public String registrationPage(Model model) {
        model.addAttribute(new User());
        model.addAttribute("title", "Sign Up");
        return "Security/signUp";
    }

    @PostMapping("/signUp")
    public String registration(@RequestParam("g-recaptcha-response") String captchaResponse,
                               @ModelAttribute("user") User user, Model model) {

        String url = String.format(UrlMappingConstants.GOOGLE_CAPTCHA_URL, captchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.getSuccess()) {
            model.addAttribute("captchaError", "Please, fill captcha");
        }

        if (userService.saveValidation(user, model) | !response.getSuccess()) {
            model.addAttribute("title", "Sign Up");
            return "Security/signUp";
        }

        user.setPassword(securityConfig.getPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return UrlMappingConstants.REDIRECT + "signIn?regSuccess";
    }

    @GetMapping("/signIn")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "regSuccess", required = false) String regSuccess,
                            @RequestParam(value = "updateSuccess", required = false) String updateSuccess,
                            Model model) {
        model.addAttribute(new User());
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("regSuccess", regSuccess != null);
        model.addAttribute("updateSuccess", updateSuccess != null);
        model.addAttribute("title", "Sign In");
        return "Security/signIn";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return UrlMappingConstants.REDIRECT + "signIn?logout";
    }
}
