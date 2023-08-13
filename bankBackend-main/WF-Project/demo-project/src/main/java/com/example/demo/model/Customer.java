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

	//Constructor

	public Customer() {
		fullName = "";
		email = "";
		phone = "";
		address = "";
		city = "";
		postalCode = "";
		region = "";
	}

	public Customer(Long customerId, @NotNull String fullName, @NotNull String email, @NotNull String phone, @NotNull String address, @NotNull String city, @NotNull String region, @NotNull String postalCode) {
		this.customerId = customerId;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
	}

	// Getters and setters

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public @NotNull String getFullName() {
		return fullName;
	}

	public void setFullName(@NotNull String fullName) {
		this.fullName = fullName;
	}

	public @NotNull String getEmail() {
		return email;
	}

	public void setEmail(@NotNull String email) {
		this.email = email;
	}

	public @NotNull String getPhone() {
		return phone;
	}

	public void setPhone(@NotNull String phone) {
		this.phone = phone;
	}

	public @NotNull String getAddress() {
		return address;
	}

	public void setAddress(@NotNull String address) {
		this.address = address;
	}

	public @NotNull String getCity() {
		return city;
	}

	public void setCity(@NotNull String city) {
		this.city = city;
	}

	public @NotNull String getRegion() {
		return region;
	}

	public void setRegion(@NotNull String region) {
		this.region = region;
	}

	public @NotNull String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(@NotNull String postalCode) {
		this.postalCode = postalCode;
	}
}
