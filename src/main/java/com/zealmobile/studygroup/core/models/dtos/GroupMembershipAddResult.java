package com.zealmobile.studygroup.core.models.dtos;


import lombok.Data;

@Data
public class GroupMembershipAddResult {
	
	private boolean succeeded;
	private String error;
	
	
	public GroupMembershipAddResult() {}
	public GroupMembershipAddResult(boolean succeeded, String error) {
		this.succeeded = succeeded;
		this.error = error;
	}
	
	

}
