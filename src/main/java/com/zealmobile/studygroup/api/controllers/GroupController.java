package com.zealmobile.studygroup.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.zealmobile.studygroup.core.entities.UserAccount;
import com.zealmobile.studygroup.core.models.dtos.AddGroupMemberPayload;
import com.zealmobile.studygroup.core.models.dtos.CreateGroupResult;
//import com.zealmobile.studygroup.core.models.dtos.CreateUserResult;
import com.zealmobile.studygroup.core.models.dtos.GroupMembershipAddResult;
import com.zealmobile.studygroup.core.models.dtos.NewGroupPayload;
//import com.zealmobile.studygroup.core.models.dtos.NewUserAccountPayload;
import com.zealmobile.studygroup.services.GroupService;
import com.zealmobile.studygroup.services.UserAccountService;

@CrossOrigin
@RestController
@RequestMapping("/groups")
public class GroupController {
	
	
	private UserAccountService _accountService;
	private GroupService _groupService;
Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	@Autowired
	public GroupController(UserAccountService accountService, GroupService groupService) {
		this._accountService = accountService;
		_groupService = groupService;
		
	}
	
	
	@PostMapping("create")
	public ResponseEntity<?> createUserAccount(@RequestBody NewGroupPayload payload) {
		try {
			//validate request appropriate via some helper class or method and throw bad request if payload is not valid
			if(payload==null || payload.getOwnerId() < 1 || payload.getName().isEmpty()) {
				return ResponseEntity.badRequest().body("Request payload is invalid. Check that group Name and owner Id are specified");
			}
			boolean isValidOwner = this._accountService.getUserById(payload.getOwnerId()).isPresent();
			if(!isValidOwner)
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unrecognized user");
			
			CreateGroupResult result = this._groupService.createGroup(payload);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			//  handle exception by logging it via appropriate logging impl.
			logger.info("could not create payload", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}
	}


	@PostMapping("{groupId}/members/add")
	public ResponseEntity<?> addGroupMember(@RequestBody AddGroupMemberPayload payload, @PathVariable int groupId) {
		try {
			//validate request appropriate via some helper class or method and throw bad request if payload is not valid
			if(payload==null) {
				return ResponseEntity.badRequest().body("Request payload is invalid. Check that group Name and owner Id are specified");
			}
			Optional<UserAccount> creator = this._accountService.getUserById(payload.getUserId());
			if(!creator.isPresent())
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unrecognized user");

			//validate that the group with given id exist and is active


			boolean creatorIsGroupAdminMember = _groupService.verifyIsGroupAdmin(payload.getUserId(), payload.getGroupId());
			if(!creatorIsGroupAdminMember)
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("User with Id: %d has no admin right on group %d", payload.getUserId(), payload.getGroupId()));

			GroupMembershipAddResult result = _groupService.addGroupMember(payload);


			return ResponseEntity.ok(result);
		} catch (Exception e) {
			//  handle exception by logging it via appropriate logging impl.
			logger.error("could not find user payload", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}
	}
	

}
