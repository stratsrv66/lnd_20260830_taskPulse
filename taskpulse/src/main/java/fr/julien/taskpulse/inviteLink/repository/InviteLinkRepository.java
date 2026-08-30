package fr.julien.taskpulse.inviteLink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.inviteLink.entity.InviteLink;

public interface InviteLinkRepository extends MongoRepository<InviteLink, String> {

    Optional<InviteLink> findById(String id);

    List<InviteLink> findByTeamId(String teamId);

    List<InviteLink> findByToken(String token);

    List<InviteLink> findByTeamIdAndToken(String teamId, String token);

    List<InviteLink> findByTeamIdAndTokenAndCreatedBy(String teamId, String token, String createdBy);

    List<InviteLink> findByTeamIdAndTokenAndCreatedByAndExpiresAt(String teamId, String token, String createdBy,
            String expiresAt);

    List<InviteLink> findByTeamIdAndTokenAndCreatedByAndExpiresAtAndMaxUses(String teamId, String token,
            String createdBy, String expiresAt, int maxUses);

}
