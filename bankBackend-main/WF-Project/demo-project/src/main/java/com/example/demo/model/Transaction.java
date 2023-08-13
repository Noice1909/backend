package com.example.demo.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@NotNull
	private Account account;

	@Column(name = "transaction_type")
	@NotNull
	private String transactionType;

	@NotNull
	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_date")
	private @NotNull Date transactionDate;

	//Constructor

	public Transaction() {
		account = null;
		transactionType = null;
		amount = null;
		transactionDate = null;
	}

	public Transaction(Long transactionId, @NotNull Account account, @NotNull String transactionType, @NotNull BigDecimal amount, @NotNull Date transactionDate) {
		this.transactionId = transactionId;
		this.account = account;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	//Getter and Setter

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public @NotNull Account getAccount() {
		return account;
	}

	public void setAccount(@NotNull Account account) {
		this.account = account;
	}

	public @NotNull String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(@NotNull String transactionType) {
		this.transactionType = transactionType;
	}

	public @NotNull BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(@NotNull BigDecimal amount) {
		this.amount = amount;
	}

	public @NotNull Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(@NotNull Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
