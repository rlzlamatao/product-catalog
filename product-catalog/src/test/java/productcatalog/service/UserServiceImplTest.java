package productcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import productcatalog.data.User;
import productcatalog.dto.UserDTO;
import productcatalog.mapper.UserMapper;
import productcatalog.repository.UserRepository;
import productcatalog.service.interfaces.UserService;

@SpringBootTest
public class UserServiceImplTest {

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testSaveUser() {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("testUsername");
		userDTO.setPassword("testPassword");
		userDTO.setFirstname("testFirstName");
		userDTO.setLastname("testLastName");
		userDTO.setEmail("testEmail@gmail.com");
		userDTO.setRole("testRole");
		
		User user = UserMapper.INSTANCE.toEntity(userDTO);
		User savedUser = user;
		
		when(userRepository.save(user)).thenReturn(savedUser);
		
		UserDTO savedUserDTO = userService.saveUser(userDTO);
		
		verify(userRepository, times(1)).save(user);
		
		assertNotNull(savedUserDTO);
		assertEquals(savedUserDTO.getId(), userDTO.getId());
		assertEquals(savedUserDTO.getUsername(), userDTO.getUsername());
		assertEquals(savedUserDTO.getPassword(), userDTO.getPassword());
		assertEquals(savedUserDTO.getFirstname(), userDTO.getFirstname());
		assertEquals(savedUserDTO.getLastname(), userDTO.getLastname());
		assertEquals(savedUserDTO.getEmail(), userDTO.getEmail());
		assertEquals(savedUserDTO.getRole(), userDTO.getRole());
	}
	
	@Test
	public void testGetUser() {
		
		Long id = 1L;
		
		User foundUser = new User();
		foundUser.setId(id);
		foundUser.setUsername("testUsername");
		foundUser.setPassword("testPassword");
		foundUser.setFirstname("testFirstName");
		foundUser.setLastname("testLastName");
		foundUser.setEmail("testEmail@gmail.com");
		foundUser.setRole("testRole");
		
		when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));
		
		UserDTO userDTO = userService.getUser(id);
		
		assertNotNull(userDTO);
		assertEquals(userDTO.getId(), foundUser.getId());
		assertEquals(userDTO.getUsername(), foundUser.getUsername());
		assertEquals(userDTO.getPassword(), foundUser.getPassword());
		assertEquals(userDTO.getFirstname(), foundUser.getFirstname());
		assertEquals(userDTO.getLastname(), foundUser.getLastname());
		assertEquals(userDTO.getEmail(), foundUser.getEmail());
		assertEquals(userDTO.getRole(), foundUser.getRole());
	}
	
	@Test
	public void testGetAllUsers() {
		
		User user1 = new User();
		user1.setId(1L);
		user1.setUsername("testUsername");
		user1.setPassword("testPassword");
		user1.setFirstname("testFirstName");
		user1.setLastname("testLastName");
		user1.setEmail("testEmail@gmail.com");
		user1.setRole("testRole");
		User user2 = new User();
		user2.setId(2L);
		user2.setUsername("testUsername");
		user2.setPassword("testPassword");
		user2.setFirstname("testFirstName");
		user2.setLastname("testLastName");
		user2.setEmail("testEmail@gmail.com");
		user2.setRole("testRole");
		
		List<User> users = Arrays.asList(user1, user2);
		
		when(userRepository.findAll()).thenReturn(users);
		
		List<UserDTO> usersDTO = userService.getAllUsers();
		
		verify(userRepository, times(1)).findAll();
		
		assertNotNull(usersDTO);
		
		assertEquals(usersDTO.size(), users.size());
		
		assertEquals(usersDTO.get(0).getId(), users.get(0).getId());
		assertEquals(usersDTO.get(0).getUsername(), users.get(0).getUsername());
		assertEquals(usersDTO.get(0).getPassword(), users.get(0).getPassword());
		assertEquals(usersDTO.get(0).getFirstname(), users.get(0).getFirstname());
		assertEquals(usersDTO.get(0).getLastname(), users.get(0).getLastname());
		assertEquals(usersDTO.get(0).getEmail(), users.get(0).getEmail());
		assertEquals(usersDTO.get(0).getRole(), users.get(0).getRole());
		
		assertEquals(usersDTO.get(1).getId(), users.get(1).getId());
		assertEquals(usersDTO.get(1).getUsername(), users.get(1).getUsername());
		assertEquals(usersDTO.get(1).getPassword(), users.get(1).getPassword());
		assertEquals(usersDTO.get(1).getFirstname(), users.get(1).getFirstname());
		assertEquals(usersDTO.get(1).getLastname(), users.get(1).getLastname());
		assertEquals(usersDTO.get(1).getEmail(), users.get(1).getEmail());
		assertEquals(usersDTO.get(1).getRole(), users.get(0).getRole());
	}
	
	@Test
	public void testUpdateUser() {
		
		Long id = 1L;
		
		UserDTO newUserDTO = new UserDTO();
		newUserDTO.setId(id);
		newUserDTO.setUsername("testUsername");
		newUserDTO.setPassword("testPassword");
		newUserDTO.setFirstname("testFirstName");
		newUserDTO.setLastname("testLastName");
		newUserDTO.setEmail("testEmail@gmail.com");
		newUserDTO.setRole("testRole");
		
		User updatedUser = new User();
		updatedUser.setId(id);
		updatedUser.setUsername("testUsername");
		updatedUser.setPassword("testPassword");
		updatedUser.setFirstname("testFirstName");
		updatedUser.setLastname("testLastName");
		updatedUser.setEmail("testEmail@gmail.com");
		updatedUser.setRole("testRole");
		
		when(userRepository.findById(id)).thenReturn(Optional.of(updatedUser));
		when(userRepository.save(updatedUser)).thenReturn(updatedUser);
		
		UserDTO updatedUserDTO = userService.updateUser(id, newUserDTO);
		
		verify(userRepository, times(1)).findById(id);
		verify(userRepository, times(1)).save(updatedUser);
		
		assertNotNull(updatedUserDTO);
		assertEquals(updatedUserDTO.getId(), newUserDTO.getId());
		assertEquals(updatedUserDTO.getUsername(), newUserDTO.getUsername());
		assertEquals(updatedUserDTO.getPassword(), newUserDTO.getPassword());
		assertEquals(updatedUserDTO.getFirstname(), newUserDTO.getFirstname());
		assertEquals(updatedUserDTO.getLastname(), newUserDTO.getLastname());
		assertEquals(updatedUserDTO.getEmail(), newUserDTO.getEmail());
		assertEquals(updatedUserDTO.getRole(), newUserDTO.getRole());
	}
	
	@Test
	public void testDeleteUser() {
		
		Long id = 1L;
		
		userService.deleteUser(id);
		
		verify(userRepository, times(1)).deleteById(id);
	}
	
}
