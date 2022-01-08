package com.zealmobile.studygroup.core.models.dtos;

import lombok.Data;

@Data
public class NewUserAccountPayload {
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
	
	public String getFullName() {
		return String.format("%s %s", this.getFirstName(), this.getLastName());
		
	}
}
