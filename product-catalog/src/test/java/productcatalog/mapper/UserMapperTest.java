package productcatalog.mapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import productcatalog.data.User;
import productcatalog.dto.UserDTO;

public class UserMapperTest {
	
	@Test
	public void testToDTO() {
		
		User user = new User();
		user.setId(1L);
		user.setUsername("testUsername");
		user.setPassword("testPassword");
		user.setFirstname("testFirstName");
		user.setLastname("testLastName");
		user.setEmail("testEmail@gmail.com");
		user.setRole("testRole");
		
		UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
	
		assertNotNull(userDTO);
		assertEquals(userDTO.getId(), user.getId());
		assertEquals(userDTO.getUsername(), user.getUsername());
		assertEquals(userDTO.getPassword(), user.getPassword());
		assertEquals(userDTO.getFirstname(), user.getFirstname());
		assertEquals(userDTO.getLastname(), user.getLastname());
		assertEquals(userDTO.getEmail(), user.getEmail());
		assertEquals(userDTO.getRole(), user.getRole());
	}
	
	@Test
	public void testToEntity() {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("testUsername");
		userDTO.setPassword("testPassword");
		userDTO.setFirstname("testFirstName");
		userDTO.setLastname("testLastName");
		userDTO.setEmail("testEmail@gmail.com");
		userDTO.setRole("testRole");
		
		User user = UserMapper.INSTANCE.toEntity(userDTO);
		
		assertNotNull(user);
		assertEquals(user.getId(), userDTO.getId());
		assertEquals(user.getUsername(), userDTO.getUsername());
		assertEquals(user.getPassword(), userDTO.getPassword());
		assertEquals(user.getFirstname(), userDTO.getFirstname());
		assertEquals(user.getLastname(), userDTO.getLastname());
		assertEquals(user.getEmail(), userDTO.getEmail());
		assertEquals(user.getRole(), userDTO.getRole());
	}

}
