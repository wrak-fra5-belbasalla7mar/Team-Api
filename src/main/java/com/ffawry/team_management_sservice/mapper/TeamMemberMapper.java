package com.ffawry.team_management_sservice.mapper;

import com.ffawry.team_management_sservice.dal.model.TeamMember;
import com.ffawry.team_management_sservice.dto.TeamMemberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    TeamMemberDto toDto(TeamMember teamMember);
    TeamMember toEntity(TeamMemberDto teamMemberDto);
}