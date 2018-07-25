package ua.mlgmag.springboot.dota2rest.services.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.UserService;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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
    public Optional<User> findById(UUID id) {
        log.info("findById {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        log.info("findByUsername {}", username);
        return userRepository.findByUsername(username);
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
                .filter(player1 -> player1.getSteamId32() != player.getSteamId32()).collect(Collectors.toSet()));
        log.info("deleteFromCollection {}", player);
        update(user);
    }

    @Override
    public List<User> findAll() {
        log.info("findAll");
        return userRepository.findAll();
    }
}
