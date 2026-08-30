package fr.julien.taskpulse.team.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "teams")
@Data
public class Team {
    @Id
    private String id;
    private String name;
    private String createdAt;
}
