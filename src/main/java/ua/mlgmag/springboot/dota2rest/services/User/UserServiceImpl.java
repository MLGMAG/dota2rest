package ua.mlgmag.springboot.dota2rest.services.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mlgmag.springboot.dota2rest.definition.UserService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        log.info("save {}", user);
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        log.info("update {}", user);
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        log.info("delete {}", user);
        userRepository.delete(user);
    }

    @Override
    public User findById(UUID id) {
        log.info("findById {}", id);
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        log.info("findByUsername {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username \"" + username + "\" not found"));
    }

    @Override
    public User findByEmail(String email) {
        log.info("findByUsername {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User with email \"" + email + "\" not found"));
    }

    @Override
    public void saveToCollection(Player player, String username) {
        User user = findByUsername(username);
        user.getPlayerCollection().add(player);
        log.info("saveToCollection {}", player);
        update(user);
    }

    @Override
    public void deleteFromCollection(Player player, String username) {
        User user = findByUsername(username);
        user.setPlayerCollection(user.getPlayerCollection().stream()
                .filter(player1 -> player1.getSteamId32() != player.getSteamId32())
                .collect(Collectors.toSet()));
        log.info("deleteFromCollection {}", player);
        update(user);
    }

    @Override
    public Boolean usernameValidation(String username, Model model) {
        try {
            findByUsername(username);
            String error = "Username already exist";
            log.info("usernameValidation {}", "Validation failed");
            model.addAttribute("DuplicateUsername", error);
            return true;
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    @Override
    public Boolean emailValidation(String email, Model model) {
        try {
            findByEmail(email);
            String error = "Email already exist";
            log.info("emailValidation {}", "Validation failed");
            model.addAttribute("DuplicateEmail", error);
            return true;
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    @Override
    public Boolean saveValidation(User user, Model model) {
        return usernameValidation(user.getUsername(), model) | emailValidation(user.getEmail(), model);
    }

    @Override
    public List<User> findAll() {
        log.info("findAll");
        return userRepository.findAll();
    }
}
