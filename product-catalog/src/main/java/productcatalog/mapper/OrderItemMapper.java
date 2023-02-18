package productcatalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import productcatalog.data.OrderItem;
import productcatalog.dto.OrderItemDTO;

@Named("OrderItemsMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderMapper.class, ProductMapper.class})
public interface OrderItemMapper {
	
	OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);
	
	
	@Named("toDTO")
	@Mappings({
		@Mapping(target = "id", source = "orderItem.id"),
		@Mapping(target = "orderDTO", source = "orderItem.order",
					qualifiedByName = {"OrderMapper", "toDTOWithoutOrderItemsDTO"}),
		@Mapping(target = "productDTO", source = "orderItem.product",
					qualifiedByName = {"ProductMapper", "toDTO"}),
		@Mapping(target = "quantity", source = "orderItem.quantity")
	})
	OrderItemDTO toDTO(OrderItem orderItem);
	
	@Named("toEntity")
	@Mappings({
		@Mapping(target = "id", source = "orderItemDTO.id"),
		@Mapping(target = "order", source = "orderItemDTO.orderDTO",
					qualifiedByName = {"OrderMapper", "toEntityWithoutOrderItems"}),
		@Mapping(target = "product", source = "orderItemDTO.productDTO",
					qualifiedByName = {"ProductMapper", "toEntity"}),
		@Mapping(target = "quantity", source = "orderItemDTO.quantity")
	})
	OrderItem toEntity(OrderItemDTO orderItemDTO);
	
	

}
