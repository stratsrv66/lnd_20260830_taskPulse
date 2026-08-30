package fr.julien.taskpulse.task.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.task.entity.Task;

public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findById(String id);

    List<Task> findByProjectId(String projectId);

    List<Task> findByAssignedTo(String assignedTo);

    List<Task> findByStatusId(String statusId);

    List<Task> findByProjectIdAndStatusId(String projectId, String statusId);

    List<Task> findByProjectIdAndAssignedTo(String projectId, String assignedTo);

    List<Task> findByProjectIdAndStatusIdAndAssignedTo(String projectId, String statusId, String assignedTo);

    List<Task> findByProjectIdAndStatusIdAndAssignedToAndTitle(String projectId, String statusId, String assignedTo,
            String title);

    List<Task> findByProjectIdAndStatusIdAndAssignedToAndTitleAndDescription(String projectId, String statusId,
            String assignedTo, String title, String description);

}
