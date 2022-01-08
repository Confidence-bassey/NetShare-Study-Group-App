package com.zealmobile.studygroup.core.models.dtos;

import com.zealmobile.studygroup.core.entities.UserAccount;

import lombok.Data;

@Data
public class CreateUserResult {
	
	private boolean succeeded;
	private String error;
	private UserAccount userAccount;
	
	
	public CreateUserResult() {}
	public CreateUserResult(boolean succeeded, String error, UserAccount account) {
		this.succeeded = succeeded;
		this.error = error;
		this.userAccount = account;
	}
	
	

}
