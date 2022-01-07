package com.zealmobile.studygroup.core.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.zealmobile.studygroup.core.models.enums.AccountStatus;
import com.zealmobile.studygroup.core.models.enums.AccountType;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserAccount {
	
	private long id;
	private UUID userId;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;
	private Date createDate;
	
	private AccountType accountType;
	private AccountStatus status;
	
}
