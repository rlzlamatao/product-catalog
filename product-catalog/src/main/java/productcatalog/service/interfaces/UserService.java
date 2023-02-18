package productcatalog.service.interfaces;

import java.util.List;

import productcatalog.dto.UserDTO;

public interface UserService {
	
	UserDTO saveUser(UserDTO userDTO);
	UserDTO getUser(Long id);
	List<UserDTO> getAllUsers();
	UserDTO updateUser(Long id, UserDTO newUserDTO);
	void deleteUser(Long id);
	
}
