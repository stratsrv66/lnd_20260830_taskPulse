package fr.julien.taskpulse.teamMember.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "teamMembers")
@Data
public class TeamMember {
    @Id
    private String id;
    private String userId;
    private String teamId;
    private String role;
    private String joinedAt;
}
