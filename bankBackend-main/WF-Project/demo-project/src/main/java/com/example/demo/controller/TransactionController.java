package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

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
}
