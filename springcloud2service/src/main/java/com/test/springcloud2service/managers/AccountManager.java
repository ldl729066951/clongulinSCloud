package com.test.springcloud2service.managers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.springcloud2service.entities.Account;
import com.test.springcloud2service.repositories.AccountRepository;
import com.test.springcloud2service.services.AccountService;


@Service
public class AccountManager implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public Collection<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account findOneAccount(Long accountID) {
		return accountRepository.findByIdAndDeleteAtIsNull(accountID);
	}

	@Override
	@Cacheable(value = "accountCache", key = "'Account_' + #p0")
	public Account findAccountByPhoneAndPassword(String phone, String password) {
		// TODO Auto-generated method stub
		return accountRepository.findByPhoneAndPasswd(phone, password);
	}

	@Override
	@Cacheable(value = "30s", key = "'Account' + #p0")
	public Account login(String phone, String password) {
		return accountRepository.login(phone, password);
	}

	@Override
	@CachePut(value = "accountCache", key = "'Account_' + #p0.phone")
	public Account addAccount(Account account) {
		if (account == null)
			return null;

		return accountRepository.saveAndFlush(account);
	}

	@Override
	@CacheEvict(value = "accountCache", key = "'Account_' + #p0.phone")
	public Account disableAccount(Account account) {
		if (account == null)
			return null;

		account.setDeleteAt(new Date());
		return accountRepository.saveAndFlush(account);
	}
}
