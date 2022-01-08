package com.zealmobile.studygroup.core.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zealmobile.studygroup.core.models.enums.GroupMembershipType;

import lombok.Data;

@Data
public class AddGroupMemberPayload {

	
	@JsonProperty("userId")
	private long userId;

	@JsonProperty("memberId")
	private int memberId;

	@JsonProperty("groupId")
	private int groupId;

	@JsonProperty("membershipType")
	private GroupMembershipType memberType;

	public  AddGroupMemberPayload(){}
	public  AddGroupMemberPayload(Long userId, int groupId, Long memberId, GroupMembershipType type){}	
	
	
	
}
