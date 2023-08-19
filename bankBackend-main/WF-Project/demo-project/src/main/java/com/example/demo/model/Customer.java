package com.example.demo.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@Size(min = 2, max = 100)
	private String fullName;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(max = 20)
	private String phone;

	@NotNull
	@Size(max = 200)
	private String address;

	@NotNull
	@Size(max = 50)
	private String city;

	@NotNull
	@Size(max = 50)
	private String region;

	@NotNull
	@Size(max = 20)
	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "account_limit") // Add the account limit column
	private int accountLimit;

	//Constructor

	public Customer() {
	}

	public Customer(Long customerId, @NotNull String fullName, @NotNull String email, @NotNull String phone, @NotNull String address, @NotNull String city, @NotNull String region, @NotNull String postalCode, int accountLimit) {
		this.customerId = customerId;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.accountLimit = accountLimit;
	}

	//Getter and Setter

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(int accountLimit) {
		this.accountLimit = accountLimit;
	}
}
