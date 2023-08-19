package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	@PostMapping("/transactions")
	public Transaction createTransaction(@Validated @RequestBody Transaction newTransaction) {
		return transactionRepository.save(newTransaction);
	}

	@PutMapping("/transactions/{id}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable(value = "id") Long transactionId,
														 @Validated @RequestBody Transaction updatedTransaction) throws ResourceNotFoundException {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));

		transaction.setAccount(updatedTransaction.getAccount());
		transaction.setTransactionType(updatedTransaction.getTransactionType());
		transaction.setAmount(updatedTransaction.getAmount());
		transaction.setTransactionDate(updatedTransaction.getTransactionDate());

		transactionRepository.save(transaction);

		return ResponseEntity.ok(transaction);
	}

	@DeleteMapping("/transactions/{id}")
	public Map<String, Boolean> deleteTransaction(@PathVariable(value = "id") Long transactionId) throws ResourceNotFoundException {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + transactionId));

		transactionRepository.delete(transaction);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Transaction has been Deleted", Boolean.TRUE);
		return response;
	}
	@GetMapping("/transactions/{customerId}")
	public List<Transaction> getTransactionsByCustomerId(@PathVariable Long customerId) {
		return transactionRepository.findByAccount_Customer_CustomerId(customerId);
	}
	@PostMapping("/process")
	public ResponseEntity<String> processTransaction(@RequestBody Map<String, Object> requestBody) {

		// Extract senderAccountId, receiverAccountId, and amount from the request body
		Long senderAccountId = ((Number) requestBody.get("senderAccountId")).longValue();
		Long receiverAccountId = ((Number) requestBody.get("receiverAccountId")).longValue();
		BigDecimal amount = new BigDecimal(requestBody.get("amount").toString());

		// Fetch sender account by senderAccountId
		Account senderAccount = accountRepository.findById(senderAccountId).orElse(null);

		// Fetch receiver account by receiverAccountId
		Account receiverAccount = accountRepository.findById(receiverAccountId).orElse(null);

		// Check if sender or receiver account is missing
		if (senderAccount == null || receiverAccount == null) {
			return ResponseEntity.badRequest().body("Invalid account(s).");
		}

		// Check if sender's balance is sufficient for the transaction
		BigDecimal senderBalance = senderAccount.getBalance();
		if (senderBalance.compareTo(amount) < 0) {
			return ResponseEntity.badRequest().body("Insufficient balance.");
		}

		// Deduct amount from sender's account balance and add to receiver's account balance
		senderAccount.setBalance(senderBalance.subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

		// Save updated balances in the account repository
		accountRepository.save(senderAccount);
		accountRepository.save(receiverAccount);

		// Create a new Transaction object and save it in the transaction repository
		Transaction transaction = new Transaction();
		transaction.setAccount(senderAccount);
		transaction.setTransactionType("Transfer");
		transaction.setAmount(amount);
		transaction.setTransactionDate(new Date());
		transactionRepository.save(transaction);

		// Return success response
		return ResponseEntity.ok("Transaction processed successfully.");
	}
}
