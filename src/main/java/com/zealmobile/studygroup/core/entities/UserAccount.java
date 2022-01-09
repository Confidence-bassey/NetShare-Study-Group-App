package com.zealmobile.studygroup.core.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zealmobile.studygroup.core.models.enums.AccountStatus;
import com.zealmobile.studygroup.core.models.enums.AccountType;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name="email")
	private String email;
	
	@Column(name = "password_hash")
	private String passwordHash;
	
	@Column(name = "join_date")
	private Date createDate;
	
	@Column(name = "account_type")
	private AccountType accountType;
	
	@Column(name = "account_status")
	private AccountStatus status;

	@Column(name="phone_number")
	private String phoneNumber;
	
	
	public UserAccount() {}
	public UserAccount withEmail(String email) {
		this.email = email;
		return this;
	}
}
