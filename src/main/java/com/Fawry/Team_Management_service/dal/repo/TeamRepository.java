package com.Fawry.Team_Management_service.dal.repo;

import com.Fawry.Team_Management_service.dal.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByManagerId(Long managerId);

}
