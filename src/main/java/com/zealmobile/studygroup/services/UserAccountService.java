package com.zealmobile.studygroup.services;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.mail.SimpleMailMessage;

//port org.springframework.mail.SimpleMailMessage;

//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zealmobile.studygroup.core.entities.UserAccount;
import com.zealmobile.studygroup.core.models.dtos.CreateUserResult;
import com.zealmobile.studygroup.core.models.dtos.NewUserAccountPayload;
import com.zealmobile.studygroup.core.models.enums.AccountStatus;
import com.zealmobile.studygroup.core.models.enums.AccountType;
import com.zealmobile.studygroup.dao.respositories.UserRespository;


@Service
public class UserAccountService {


	Logger _logger = LoggerFactory.getLogger(UserAccountService.class);

	@Autowired
	private UserRespository _userRespository;
	private EmailService _emailSender;

	public UserAccountService(UserRespository userRespository, EmailService emailSender) {
		_userRespository = userRespository;	
		_emailSender = emailSender;	
	}


	Logger logger = LoggerFactory.getLogger("UserAccountService");
		
	public CreateUserResult createUserAccount(NewUserAccountPayload user) {
		try {
			boolean userExist = this._userRespository.exists(Example.of(new UserAccount().withEmail(user.getEmail())));
			if(userExist) {
				return new CreateUserResult(false, String.format("Account with email: %s already exist", user.getEmail()), null);
			}
			UserAccount accountToCreate = this._buildNewUserAccountObjectFromPayload(user);
			UserAccount createdAccount = this._userRespository.save(accountToCreate);
			
			//here send confirmation email | sms depending on which field is populated or construct and publish email object into some queue.
			SimpleMailMessage emailMessage = this._createAccountActivationEmail(createdAccount);
			_emailSender.sendEmail(emailMessage);
			return new CreateUserResult(true,"", createdAccount);
			
		}
		catch (Exception e) {
			// handle exception using a an appropriate logging impl.
			logger.info("An exception occured",e);
			throw e;
		}
	}
	

	public Optional<UserAccount> getUserById(long id) {
		return this._userRespository.findById(id);
	}

	public List<UserAccount> getUserAccounts() {
		try{
			return this._userRespository.findAll();
		}
		catch (Exception exce) {
			throw exce;
		}
	}
	
	public List<UserAccount> getUserAccounts() {
		try{
			return this._userRespository.findAll();
		}
		catch (Exception exce) {
			throw exce;
		}
	}

	public Optional<UserAccount> getAccountAccountByEmail(String emialAddress) {
		return this._userRespository.findOne(Example.of(new UserAccount().withEmail(emialAddress)));	
	}
	
	public void updateAccount(UserAccount user) {
		_userRespository.saveAndFlush(user);
	}
			
	private UserAccount _buildNewUserAccountObjectFromPayload(NewUserAccountPayload userAccountPayload) {
		UserAccount account = new UserAccount();
		
		account.setAccountType(AccountType.User);
		account.setCreateDate(new Date());
		account.setEmail(userAccountPayload.getEmail());
		account.setFirstName(userAccountPayload.getFirstName());
		account.setLastName(userAccountPayload.getLastName());
		account.setEmail(userAccountPayload.getEmail());
		account.setPhoneNumber(userAccountPayload.getPhoneNumber());
		account.setStatus(AccountStatus.New);
		account.setUserId(UUID.randomUUID().toString());
		_logger.info("set account uuid is: "+ account.getUserId());
		
		return account;
	}

	private SimpleMailMessage _createAccountActivationEmail(UserAccount user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(String.format("Welcome to Study group, %s! ", user.getFirstName()));
		email.setTo(user.getEmail());
		email.setText(String.format("Dear %s\nThank you for signing up on study group App. \nPlease activate your account by clicking on this %s", user.getFirstName(), generateLink(user)));
		return email;

	}

	private String generateLink(UserAccount user) {
		String userACtivationCode = new String(Base64.getEncoder().encode(user.getEmail().getBytes()));
		return String.format("<a href=\"http://localhost:8080/accounts/%s/activate\">activation link</a>", userACtivationCode);
	}
}
