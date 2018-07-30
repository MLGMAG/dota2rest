package ua.mlgmag.springboot.dota2rest.definition;

import org.springframework.ui.Model;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.User;

import java.util.UUID;

public interface UserService extends GenericService<User, UUID> {

    void update(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    void saveToCollection(Player player, String username);

    void deleteFromCollection(Player player, String username);

    Boolean usernameValidation(String username, Model model);

    Boolean emailValidation(String email, Model model);

    Boolean saveValidation(User user, Model model);

    Boolean updateValidation(User updatedUser, Model model);

}
