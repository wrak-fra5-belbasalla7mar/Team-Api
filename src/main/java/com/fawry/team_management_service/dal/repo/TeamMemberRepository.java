package com.Fawry.Team_Management_service.dal.repo;

import com.Fawry.Team_Management_service.dal.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByTeamId(Long teamId);
    Optional<TeamMember> findByUserId(Long userId);
}