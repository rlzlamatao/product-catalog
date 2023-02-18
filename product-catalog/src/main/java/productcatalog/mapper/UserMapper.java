package productcatalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import productcatalog.data.User;
import productcatalog.dto.UserDTO;

@Named("UserMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Named("toDTO")
	@Mappings({
		@Mapping(target = "id", source = "user.id"),
		@Mapping(target = "username", source = "user.username"),
		@Mapping(target = "password", source = "user.password"),
		@Mapping(target = "firstname", source = "user.firstname"),
		@Mapping(target = "lastname", source = "user.lastname"),
		@Mapping(target = "email", source = "user.email"),
		@Mapping(target = "role", source = "user.role")
	})
	UserDTO toDTO(User user);
	
	@Named("toEntity") 
	@Mappings({
		@Mapping(target = "id", source = "userDTO.id"),
		@Mapping(target = "username", source = "userDTO.username"),
		@Mapping(target = "password", source = "userDTO.password"),
		@Mapping(target = "firstname", source = "userDTO.firstname"),
		@Mapping(target = "lastname", source = "userDTO.lastname"),
		@Mapping(target = "email", source = "userDTO.email"),
		@Mapping(target = "role", source = "userDTO.role")
	})
	User toEntity(UserDTO userDTO);
}
