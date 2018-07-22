package ua.mlgmag.springboot.dota2rest.services.User;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.UserService;
import ua.mlgmag.springboot.dota2rest.enums.Authority;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;
import ua.mlgmag.springboot.dota2rest.repository.UserRepository;
import ua.mlgmag.springboot.dota2rest.services.SecurityServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SecurityServiceImpl securityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityServiceImpl securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public void save(User user) {
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthorities(ImmutableSet.of(Authority.USER));
        log.info("save {}", user);
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        log.info("update {}", user);
        userRepository.saveAndFlush(user);
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
    public void saveToCollection(Player player) {
        User user = findByUsername(securityService.findLoggedInUsername());
        List<Player> playerCollection = user.getPlayerCollection();
        if (playerCollection == null) {
            user.setPlayerCollection(ImmutableList.of(player));
            save(user);
            return;
        }
        playerCollection.add(player);
        user.setPlayerCollection(playerCollection);
        save(user);
    }

    @Override
    public List<User> findAll() {
        log.info("findAll");
        return userRepository.findAll();
    }
}
