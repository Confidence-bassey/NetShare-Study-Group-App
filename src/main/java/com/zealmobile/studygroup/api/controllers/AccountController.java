package com.zealmobile.studygroup.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zealmobile.studygroup.core.models.dtos.CreateUserResult;
import com.zealmobile.studygroup.core.models.dtos.NewUserAccountPayload;
import com.zealmobile.studygroup.services.UserAccountService;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	
	private UserAccountService _accountService;
	
	
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
			// TODO: handle exception by logging it via appropriate logging impl.
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getAccounts(){
		try {
			var userAccountsList = this._accountService.getUserAccounts();
			return ResponseEntity.ok(userAccountsList);
		} catch (Exception e) {
			// TODO: handle exception by logging it via appropriate logging impl.
			return ResponseEntity.internalServerError().body(e.getMessage());
			
		}	
	}
	

}
