package productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalog.data.User;
import productcatalog.dto.UserDTO;
import productcatalog.mapper.UserMapper;
import productcatalog.repository.UserRepository;
import productcatalog.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		User user = UserMapper.INSTANCE.toEntity(userDTO);
		User savedUser = userRepository.save(user);
		UserDTO savedUserDTO = UserMapper.INSTANCE.toDTO(savedUser);		
		return savedUserDTO;
	}

	@Override
	public UserDTO getUser(Long id) {
		User foundUser = userRepository.findById(id).orElseThrow();
		UserDTO foundUserDTO = UserMapper.INSTANCE.toDTO(foundUser);
		return foundUserDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> usersDTO = users.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
		return usersDTO;
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO newUserDTO) {
		User updatedUser = userRepository.findById(id)
				.map(u -> {
					u.setFirstname(newUserDTO.getFirstname());
					u.setLastname(newUserDTO.getLastname());
					u.setEmail(newUserDTO.getEmail());
					u.setUsername(newUserDTO.getUsername());
					u.setPassword(newUserDTO.getPassword());
					u.setRole(newUserDTO.getRole());
					return userRepository.save(u);
				}).orElse(null);
		UserDTO updatedUserDTO = UserMapper.INSTANCE.toDTO(updatedUser);
		return updatedUserDTO;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

}
