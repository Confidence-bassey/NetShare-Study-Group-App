package com.zealmobile.studygroup.dao.respositories;

import com.zealmobile.studygroup.core.entities.Group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    
}
