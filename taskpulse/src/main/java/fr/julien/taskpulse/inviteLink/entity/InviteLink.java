package fr.julien.taskpulse.inviteLink.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "inviteLinks")
@Data
public class InviteLink {
    @Id
    private String id;
    private String teamId;
    private String token;
    private String createdBy;
    private String expiresAt;
    private int maxUses;
}
