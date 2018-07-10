package ua.mlgmag.springboot.dota2rest.definition;

import ua.mlgmag.springboot.dota2rest.model.User;

public interface UserService extends GenericService<User, String> {

    void update(User user);

    User findByUsername(String username);

}
