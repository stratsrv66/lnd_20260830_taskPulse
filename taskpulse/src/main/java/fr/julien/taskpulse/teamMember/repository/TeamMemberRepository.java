package fr.julien.taskpulse.teamMember.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.julien.taskpulse.teamMember.entity.TeamMember;

public interface TeamMemberRepository extends MongoRepository<TeamMember, String> {

    Optional<TeamMember> findById(String id);

    List<TeamMember> findByTeamId(String teamId);

    List<TeamMember> findByUserId(String userId);

    List<TeamMember> findByTeamIdAndUserId(String teamId, String userId);

    List<TeamMember> findByTeamIdAndUserIdAndRole(String teamId, String userId, String role);

    List<TeamMember> findByTeamIdAndUserIdAndRoleAndJoinedAt(String teamId, String userId, String role,
            String joinedAt);

}
