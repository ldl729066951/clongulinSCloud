package com.test.springcloud2service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.test.springcloud2service.entities.Account;

@Service
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByPhoneAndPasswd(String phone, String passwd);

	@Query("select a from Account a where a.phone=?1 and a.passwd=?2")
	public Account login(String phone, String password);

	public Account findByPhoneAndDeleteAtIsNull(String phone);

	@Query("select a from Account a where a.id in ?1 and a.deleteAt is null")
	public List<Account> findByIdIn(List<Long> accountIds);

	public Account findByIdAndDeleteAtIsNull(Long accountId);
}
