package ua.mlgmag.springboot.dota2rest.definition;

import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;

import java.util.UUID;

public interface UserService extends GenericService<User, UUID> {

    void update(User user);

    User findByUsername(String username);

    void saveToCollection(Player player, String username);

    void deleteFromCollection(Player player, String username);

}
