package productcatalog.controller.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import productcatalog.dto.UserDTO;
import productcatalog.service.interfaces.UserService;

@WebMvcTest(UserAPI.class)
public class UserAPITest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void testGetAllUsers() throws Exception {
		
		UserDTO user1DTO = new UserDTO();
		user1DTO.setId(1L);
		user1DTO.setUsername("user1 username");
		user1DTO.setPassword("user1 password");
		user1DTO.setFirstname("user1 firstname");
		user1DTO.setLastname("user1 lastname");
		user1DTO.setRole("user1 role");
		user1DTO.setEmail("user1 email");
		UserDTO user2DTO = new UserDTO();
		user2DTO.setId(2L);
		user2DTO.setUsername("user2 username");
		user2DTO.setPassword("user2 password");
		user2DTO.setFirstname("user2 firstname");
		user2DTO.setLastname("user2 lastname");
		user2DTO.setRole("user2 role");
		user2DTO.setEmail("user2 email");
		
		List<UserDTO> usersDTO = Arrays.asList(user1DTO, user2DTO);
		
		when(userService.getAllUsers()).thenReturn(usersDTO);
		
		mvc.perform(get("/user")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(usersDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].username", is("user1 username")))
			.andExpect(jsonPath("$[0].password", is("user1 password")))
			.andExpect(jsonPath("$[0].firstname", is("user1 firstname")))
			.andExpect(jsonPath("$[0].lastname", is("user1 lastname")))
			.andExpect(jsonPath("$[0].role", is("user1 role")))
			.andExpect(jsonPath("$[0].email", is("user1 email")))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].username", is("user2 username")))
			.andExpect(jsonPath("$[1].password", is("user2 password")))
			.andExpect(jsonPath("$[1].firstname", is("user2 firstname")))
			.andExpect(jsonPath("$[1].lastname", is("user2 lastname")))
			.andExpect(jsonPath("$[1].role", is("user2 role")))
			.andExpect(jsonPath("$[1].email", is("user2 email")));
			
		verify(userService, times(1)).getAllUsers();
	}
	
	@Test
	public void testGetUserById() throws Exception {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("user1 username");
		userDTO.setPassword("user1 password");
		userDTO.setFirstname("user1 firstname");
		userDTO.setLastname("user1 lastname");
		userDTO.setRole("user1 role");
		userDTO.setEmail("user1 email");
		
		when(userService.getUser(1L)).thenReturn(userDTO);
		
		mvc.perform(get("/user/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(userDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.username", is("user1 username")))
			.andExpect(jsonPath("$.password", is("user1 password")))
			.andExpect(jsonPath("$.firstname", is("user1 firstname")))
			.andExpect(jsonPath("$.lastname", is("user1 lastname")))
			.andExpect(jsonPath("$.role", is("user1 role")))
			.andExpect(jsonPath("$.email", is("user1 email")));
		
		verify(userService, times(1)).getUser(1L);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("user1 username");
		userDTO.setPassword("user1 password");
		userDTO.setFirstname("user1 firstname");
		userDTO.setLastname("user1 lastname");
		userDTO.setRole("user1 role");
		userDTO.setEmail("user1 email");
		
		when(userService.saveUser(userDTO)).thenReturn(userDTO);
		
		mvc.perform(post("/user")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(userDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.username", is("user1 username")))
			.andExpect(jsonPath("$.password", is("user1 password")))
			.andExpect(jsonPath("$.firstname", is("user1 firstname")))
			.andExpect(jsonPath("$.lastname", is("user1 lastname")))
			.andExpect(jsonPath("$.role", is("user1 role")))
			.andExpect(jsonPath("$.email", is("user1 email")));
		
		verify(userService, times(1)).saveUser(userDTO);
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("user1 username");
		userDTO.setPassword("user1 password");
		userDTO.setFirstname("user1 firstname");
		userDTO.setLastname("user1 lastname");
		userDTO.setRole("user1 role");
		userDTO.setEmail("user1 email");
		
		when(userService.updateUser(1L, userDTO)).thenReturn(userDTO);
		
		mvc.perform(put("/user/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(userDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.username", is("user1 username")))
			.andExpect(jsonPath("$.password", is("user1 password")))
			.andExpect(jsonPath("$.firstname", is("user1 firstname")))
			.andExpect(jsonPath("$.lastname", is("user1 lastname")))
			.andExpect(jsonPath("$.role", is("user1 role")))
			.andExpect(jsonPath("$.email", is("user1 email")));
		
		verify(userService, times(1)).updateUser(1L, userDTO);
	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
