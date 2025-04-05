package com.ffawry.team_management_sservice.dal.repo;

import com.ffawry.team_management_sservice.dal.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByManagerId(Long managerId);

}
