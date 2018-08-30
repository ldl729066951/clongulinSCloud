package com.test.springcloud2service.services;

import java.util.Collection;

import com.test.springcloud2service.entities.Account;

public interface AccountService {
	public Collection<Account> getAllAccounts();

	public Account findOneAccount(Long accountID);

	public Account findAccountByPhoneAndPassword(String phone, String password);

	public Account login(String phone, String password);

	public Account addAccount(Account account);

	public Account disableAccount(Account account);
}
