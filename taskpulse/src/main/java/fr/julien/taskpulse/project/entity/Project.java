package fr.julien.taskpulse.project.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "projects")
@Data
public class Project {
    @Id
    private String id;
    private String teamId;
    private String name;
    private String description;
    private String createdAt;
}
