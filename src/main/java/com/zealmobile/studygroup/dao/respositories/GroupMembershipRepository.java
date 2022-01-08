package com.zealmobile.studygroup.dao.respositories;

import com.zealmobile.studygroup.core.entities.GroupMember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembershipRepository extends JpaRepository<GroupMember,Integer> {
    
}
