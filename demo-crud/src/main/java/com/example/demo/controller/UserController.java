package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//*import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;
	
	@Autowired
    public UserController(UserService service) {
        this.service = service;
    }
	
	@GetMapping
	public List<User> getUsers() {
		return service.getAllUsers();
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		user.setId(null);
		return service.addUser(user);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		return service.updateUser(id, user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	/*@Autowired
	private UserService userService;
	
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.findALL();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		User user = userService.findById(id);
		if (user == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		User updated = userService.update(id, user);
		if(updated == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		boolean deleted = userService.delete(id);
		if(!deleted) return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}*/
}
