package com.example.registration.rest;

import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registration.dto.UserDTO;
import com.example.registration.repository.UserJpaRespository;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
public class UserRegistrationRestController {

	public static final Logger logger = (Logger) LoggerFactory.getLogger(UserRegistrationRestController.class);
	
	private final UserJpaRespository userJpaRepoJpaRespository;
	
	@Autowired
	public UserRegistrationRestController(UserJpaRespository userJpaRespository) {
	
		// TODO Auto-generated constructor stub
		this.userJpaRepoJpaRespository = userJpaRespository;
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<UserDTO>> listAllUsers(){
		
		List<UserDTO> users =userJpaRepoJpaRespository.findAll();
		
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user){
		
		
		
		logger.info("creating user: {}", user);
		
		UserDTO newUser = new UserDTO();
		
		newUser =userJpaRepoJpaRespository.save(user);
		
		return new ResponseEntity<UserDTO>(newUser,HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id){
		
		Optional<UserDTO> optionalUser = userJpaRepoJpaRespository.findById(id);
		
		if(optionalUser.isPresent()) {
			
			UserDTO user = optionalUser.get();
			
			return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
			
		}
		else {
			
			return new ResponseEntity<String>("can't find that user", HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable(value = "id") Long id, @RequestBody UserDTO user){
		
		
		//fetch the user based on the ID
		Optional<UserDTO> optionalUser = userJpaRepoJpaRespository.findById(id);
		
		//check if the user exists
		if(optionalUser.isPresent()) {
			
			//get the user and perform update operation
			UserDTO oldUser = optionalUser.get();
			
			//perform the update operation
			oldUser.setName(user.getName());
			oldUser.setEmail(user.getEmail());
			oldUser.setAddress(user.getAddress());
			
			//assign the updated olduser to a new user variable after making changes
			UserDTO newUser = userJpaRepoJpaRespository.save(oldUser);
			
			return ResponseEntity.ok(newUser);
			
		}
		
		else {
			
			//return the error message saying that the user wasn't found
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find a user with that ID");
			
		}
	
	}
	 
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id){
		
			//first, look for the user
			Optional<UserDTO> user =userJpaRepoJpaRespository.findById(id);
			
			//check if the user exists
			if(user.isPresent()) {
				
				//hence, if the user exists, it is his misfortune
				//delete the user
				userJpaRepoJpaRespository.deleteById(id);
				
				return new ResponseEntity<String>("successfull deleted the user", HttpStatus.OK);
				
			}
			
			else {
				
				return new ResponseEntity<String>("User doesn't exists", HttpStatus.NOT_FOUND);
			}
			
			
	}
		
}
