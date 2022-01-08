package com.zealmobile.studygroup.core.models.dtos;


import lombok.Data;

@Data
public class CreateGroupResult {
	
	private boolean succeeded;
	private String error;
	
	
	public CreateGroupResult() {}
	public CreateGroupResult(boolean succeeded, String error) {
		this.succeeded = succeeded;
		this.error = error;
	}
	
	

}
