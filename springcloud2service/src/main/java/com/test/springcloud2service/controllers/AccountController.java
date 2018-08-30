package com.test.springcloud2service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.springcloud2service.entities.Account;
import com.test.springcloud2service.services.AccountService;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	public Account getAccount(Long accountID) {

		return accountService.findOneAccount(accountID);
	}
}