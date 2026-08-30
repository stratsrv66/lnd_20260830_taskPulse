package fr.julien.taskpulse.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.project.entity.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findById(String id);

    List<Project> findByName(String name);

}
