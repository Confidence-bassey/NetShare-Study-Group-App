package com.zealmobile.studygroup.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.var;

import java.util.Base64;

import com.zealmobile.studygroup.core.models.dtos.CreateUserResult;
import com.zealmobile.studygroup.core.models.dtos.NewUserAccountPayload;
import com.zealmobile.studygroup.services.UserAccountService;
import com.zealmobile.studygroup.core.models.enums.AccountStatus;


@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	
	private UserAccountService _accountService;

	Logger logger = LoggerFactory.getLogger(AccountController.class);
		
	@Autowired
	public AccountController(UserAccountService accountService) {
		this._accountService = accountService;
		
	}
	
	
	@PostMapping("create")
	public ResponseEntity<?> createUserAccount(@RequestBody NewUserAccountPayload payload) {
		try {
			//validate request appropriate via some helper class or method and throw bad request if payload is not valid
			if(payload==null || payload.getEmail().isEmpty() || payload.getFullName().isEmpty()) {
				return ResponseEntity.badRequest().body("Request payload is invalid");
			}
			CreateUserResult result = _accountService.createUserAccount(payload);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			// handle exception by logging it via appropriate logging impl.
			logger.trace("payload not found", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getAccounts(){
		try {
			var userAccountsList = this._accountService.getUserAccounts();
			return ResponseEntity.ok(userAccountsList);
		} catch (Exception e) {
			//  handle exception by logging it via appropriate logging impl.
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}	
	}

	@GetMapping("{activation_code}/activate")
	public ResponseEntity<?> activateAccount(@PathVariable String activation_code) {

		if(activation_code.isEmpty())
			return ResponseEntity.badRequest().body("Invalid activation code");
		var code = new String(Base64.getDecoder().decode(activation_code));

		var targetAccount = _accountService.getAccountAccountByEmail(code);
		if(!targetAccount.isPresent())
			return ResponseEntity.badRequest().body("Invalid activation code. Account does not exist");
		targetAccount.get().setStatus(AccountStatus.Active);
		_accountService.updateAccount(targetAccount.get());
		
		return ResponseEntity.ok(String.format("Thank you %s,\nYour group study account is now active. Go ahead anmd enjoy", targetAccount.get().getFirstName()));
	}

}
