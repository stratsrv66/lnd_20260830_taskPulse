package fr.julien.taskpulse.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.user.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByEmailOrUsername(String email, String username);

    List<User> findByEmailAndUsername(String email, String username);

}
