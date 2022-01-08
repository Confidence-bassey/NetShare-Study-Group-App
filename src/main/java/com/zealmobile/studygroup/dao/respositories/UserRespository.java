package com.zealmobile.studygroup.dao.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zealmobile.studygroup.core.entities.UserAccount;

public interface UserRespository extends JpaRepository<UserAccount, Long> {

}
