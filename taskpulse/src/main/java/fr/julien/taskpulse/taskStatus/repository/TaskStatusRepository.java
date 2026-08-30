package fr.julien.taskpulse.taskStatus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.taskStatus.entity.TaskStatus;

public interface TaskStatusRepository extends MongoRepository<TaskStatus, String> {

    Optional<TaskStatus> findById(String id);

    List<TaskStatus> findByProjectId(String projectId);

    List<TaskStatus> findByProjectIdAndName(String projectId, String name);

}
