package fr.julien.taskpulse.team.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.team.entity.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

    Optional<Team> findById(String id);

    List<Team> findByName(String name);

}
