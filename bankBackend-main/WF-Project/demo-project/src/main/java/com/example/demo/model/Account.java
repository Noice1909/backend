package com.example.demo.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "Account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountId;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@NotNull
	private Customer customer;

	@Column(name = "account_type")
	@NotNull
	private String accountType;

	@NotNull
	private BigDecimal balance;

	//Constructor

	public Account() {
		customer = null;
		accountType = null;
		balance = null;
	}

	public Account(Long accountId, @NotNull Customer customer, @NotNull String accountType, @NotNull BigDecimal balance) {
		this.accountId = accountId;
		this.customer = customer;
		this.accountType = accountType;
		this.balance = balance;
	}

	// Getters and setters
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public @NotNull Customer getCustomer() {
		return customer;
	}

	public void setCustomer(@NotNull Customer customer) {
		this.customer = customer;
	}

	public @NotNull String getAccountType() {
		return accountType;
	}

	public void setAccountType(@NotNull String accountType) {
		this.accountType = accountType;
	}

	public @NotNull BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(@NotNull BigDecimal balance) {
		this.balance = balance;
	}
}
