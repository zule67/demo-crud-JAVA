package com.example.demo;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

class DemoCrudApplicationTests {

	@Mock
	private UserRepository repository;
	
	@InjectMocks
	private UserService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testingGetAllUsers() {
		User u1 = new User (1L, "a", "a@a.com");
		User u2 = new User (2L, "b", "b@b.com");
		when(repository.findAll()).thenReturn(Arrays.asList(u1,u2));
		
		List<User> result = service.getAllUsers();
		
		assertEquals(2, result.size());
		assertEquals("a", u1.getName());
	}
	
	@Test
	void testAddUser() {
		User u = new User(null, "a", "a@a.com");
		User saved = new User(5L, "b", "b@b.com");
		
		when(repository.save(u)).thenReturn(saved);
		
		User result = service.addUser(u);
		
		assertEquals(5L, result.getId());
		verify(repository, times(1)).save(u);
	}
	
	@Test
	void testUpdateUserSuccess() {
		User exist = new User(1L, "a", "a@a.com");
		User update = new User(null, "b", "b@b.com");
		
		when(repository.findAll()).thenReturn(Arrays.asList(exist));
		when(repository.save(exist)).thenReturn(exist);
		
		User result = service.updateUser(1L, update);
		
		assertEquals("b", result.getName());
		assertEquals("b@b.com", result.getEmail());
		verify(repository, times(1)).findAll();
		verify(repository, times(1)).save(exist);
	}
		
	@Test
	void testDeleteUser() {		
		service.deleteUser(1L);
		
		verify(repository, times(1)).deleteById(1L);
	}
}
