package com.zealmobile.studygroup.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
	public UserAccountService(UserRespository userRespository) {
		_userRespository = userRespository;
		
	}
	
	
	public CreateUserResult createUserAccount(NewUserAccountPayload user) {
		try {
			boolean userExist = this._userRespository.exists(Example.of(new UserAccount().withEmail(user.getEmail())));
			if(userExist) {
				return new CreateUserResult(false, String.format("Account with email: %s already exist", user.getEmail()), null);
			}
			UserAccount accountToCreate = this._buildNewUserAccountObjectFromPayload(user);
			UserAccount createdAccount = this._userRespository.save(accountToCreate);
			
			//here send confirmation email | sms depending on which field is populated or construct and publish email object into some queue.
			
			return new CreateUserResult(true,"", createdAccount);
			
		}
		catch (Exception e) {
			// TODO: handle exception using a an appropriate logging impl.
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
}
