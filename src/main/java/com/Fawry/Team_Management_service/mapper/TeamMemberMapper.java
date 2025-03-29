package com.Fawry.Team_Management_service.mapper;

import com.Fawry.Team_Management_service.dal.model.TeamMember;
import com.Fawry.Team_Management_service.dto.TeamMemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    TeamMemberDto toDto(TeamMember teamMember);
    TeamMember toEntity(TeamMemberDto teamMemberDto);
}