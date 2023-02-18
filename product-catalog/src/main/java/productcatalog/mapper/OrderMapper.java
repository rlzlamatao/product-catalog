package productcatalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import productcatalog.data.Order;
import productcatalog.dto.OrderDTO;

@Named("OrderMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface OrderMapper {
	
	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	@Named("toDTO")
	@Mappings({
		@Mapping(target = "id", source = "order.id"),
		@Mapping(target = "orderItemsDTO", source = "order.orderItems"),
		@Mapping(target = "userDTO", source = "order.user",
					qualifiedByName = {"UserMapper", "toDTO"}),
		@Mapping(target = "orderDate", source = "order.orderDate")
	})
	OrderDTO toDTO(Order order);
	
	@Named("toDTOWithoutOrderItemsDTO")
	@Mappings({
		@Mapping(target = "id", source = "order.id"),
		@Mapping(target = "orderItemsDTO", ignore = true),
		@Mapping(target = "userDTO", source = "order.user"),
		@Mapping(target = "orderDate", source = "order.orderDate")
	})
	OrderDTO toDTOWithoutOrderItemsDTO(Order order);
	
	@Named("toEntity")
	@Mappings({
		@Mapping(target = "id", source = "orderDTO.id"),
		@Mapping(target = "orderItems", source = "orderDTO.orderItemsDTO"),
		@Mapping(target = "user", source = "orderDTO.userDTO",
					qualifiedByName = {"UserMapper", "toEntity"}),
		@Mapping(target = "orderDate", source = "orderDTO.orderDate")
	})
	Order toEntity(OrderDTO orderDTO);
	
	@Named("toEntityWithoutOrderItems")
	@Mappings({
		@Mapping(target = "id", source = "orderDTO.id"),
		@Mapping(target = "orderItems", ignore = true),
		@Mapping(target = "user", source = "orderDTO.userDTO"),
		@Mapping(target = "orderDate", source = "orderDTO.orderDate")
	})
	Order toEntityWithoutOrderItems(OrderDTO orderDTO);
}
