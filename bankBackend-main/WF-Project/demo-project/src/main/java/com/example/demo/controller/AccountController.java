package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@PostMapping("/accounts")
	public Account createAccount(@Validated @RequestBody Account newAccount) {
		return accountRepository.save(newAccount);
	}

	@PutMapping("/accounts/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
												 @Validated @RequestBody Account updatedAccount) throws ResourceNotFoundException {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));

		account.setCustomer(updatedAccount.getCustomer());
		account.setAccountType(updatedAccount.getAccountType());
		account.setBalance(updatedAccount.getBalance());
		accountRepository.save(account);

		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/accounts/{id}")
	public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId) throws ResourceNotFoundException {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));

		accountRepository.delete(account);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Account has been Deleted", Boolean.TRUE);
		return response;
	}
//	@GetMapping("/accounts/user/{userId}")
//	public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
//		List<Account> accounts = accountRepository.findByCustomer_UserId(userId);
//
//		if (!accounts.isEmpty()) {
//			return ResponseEntity.ok(accounts);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
}
