package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	public User addUser(User user) {
		return repository.save(user);
	}
	
	public User updateUser(Long id, User updatedUser) {
		User exist = getAllUsers()
				.stream()
				.filter(x -> x.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("User not found"));
		exist.setName(updatedUser.getName());
		exist.setEmail(updatedUser.getEmail());
		
		return addUser(exist);
	}
	
	public void deleteUser(Long id) {
		repository.deleteById(id);
	}
	
	/*private Map<Long,  User> users = new HashMap<>();
	private Long nextId = 1L;
	
	public List<User> findALL() {
		return new ArrayList<>(users.values());
	}
	
	public User findById(Long id) {
		return users.get(id);
	}
	
	public User save(User user) {
		user.setId(nextId++);
		users.put(user.getId(), user);
		return user;
	}
	
	public User update(Long id, User user) {
		if(!users.containsKey(id)) return null;
		user.setId(id);
		users.put(id, user);
		return user;
	}
	
	public boolean delete(Long id) {
		return users.remove(id) != null;
	}*/
}
