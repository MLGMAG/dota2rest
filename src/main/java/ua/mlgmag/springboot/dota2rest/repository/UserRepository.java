package ua.mlgmag.springboot.dota2rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.mlgmag.springboot.dota2rest.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

}
