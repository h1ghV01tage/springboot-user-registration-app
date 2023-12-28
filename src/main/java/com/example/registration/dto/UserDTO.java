package com.example.registration.dto;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class UserDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;
	
	@NotBlank(message = "name can't be blank")
	@Column(name="NAME")
	private String name;
	
	@NotBlank(message = "address can't be blank")
	@Column(name = "ADDRESS")
	private String address;
	
	@NotBlank(message = "email can't be blank")
	@Email
	@Column(name = "EMAIL")
	private String email;
	
}
