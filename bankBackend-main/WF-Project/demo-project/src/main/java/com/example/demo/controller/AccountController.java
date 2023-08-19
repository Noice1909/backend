package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
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
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@PostMapping("/accounts")
	public ResponseEntity<Map<String, Object>> createAccount(@Validated @RequestBody Account newAccount) {
		// Assuming you have a method to check the customer's account limit
		boolean hasReachedAccountLimit = newAccount.getCustomer().getAccountLimit() > 0;

		Map<String, Object> response = new HashMap<>();
		if (hasReachedAccountLimit) {
			response.put("success", false);
			response.put("message", "Customer has reached the account limit");
			return ResponseEntity.badRequest().body(response);
		} else {
			newAccount.getCustomer().setAccountLimit(newAccount.getCustomer().getAccountLimit()-1);
			Account createdAccount = accountRepository.save(newAccount);
			response.put("success", true);
			response.put("message", "Account created successfully");
			response.put("account", createdAccount);
			return ResponseEntity.ok(response);
		}
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
	@GetMapping("/by-customer/{customerId}")
	public List<Account> getAccountsByCustomer(@PathVariable Long customerId) {
		// You would need to fetch the customer object by their ID
		Customer customer = customerRepository.findById(customerId).orElse(null);

		if (customer == null) {
			// Handle the case where customer is not found
			return null;
		}

		return accountRepository.findByCustomer(customer);
	}
}
