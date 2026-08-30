package fr.julien.taskpulse.taskStatus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "taskStatus")
@Data
public class TaskStatus {
    @Id
    private String id;
    private String projectId;
    private String name;
    private int position;
}
