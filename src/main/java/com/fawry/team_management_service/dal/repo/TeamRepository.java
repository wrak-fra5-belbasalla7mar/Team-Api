package com.Fawry.Team_Management_service.dal.repo;

import com.Fawry.Team_Management_service.dal.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByManagerId(Long managerId);
}
