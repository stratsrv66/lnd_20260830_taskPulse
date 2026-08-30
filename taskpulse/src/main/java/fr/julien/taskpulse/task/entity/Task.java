package fr.julien.taskpulse.task.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private String id;
    private String projectId;
    private String title;
    private String description;
    private String statusId;
    private String assignedTo;
    private String createdAt;
    private String updatedAt;
}
