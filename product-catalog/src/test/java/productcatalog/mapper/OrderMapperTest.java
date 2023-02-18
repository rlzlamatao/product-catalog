package productcatalog.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import productcatalog.data.Order;
import productcatalog.data.OrderItem;
import productcatalog.data.User;
import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.UserDTO;

public class OrderMapperTest {
	
	@Test
	public void testToDTO() {
		
		Order order = new Order();
		order.setId(1L);
		order.setOrderDate(new Date());
		order.setUser(new User());
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		order.setOrderItems(orderItems);
				
		OrderDTO orderDTO = OrderMapper.INSTANCE.toDTO(order);
		
		assertNotNull(orderDTO);
		assertEquals(orderDTO.getId(), order.getId());
		assertEquals(orderDTO.getOrderDate(), order.getOrderDate());
		assertNotNull(orderDTO.getUserDTO());
		assertNotNull(orderDTO.getOrderItemsDTO());
		assertEquals(orderDTO.getOrderItemsDTO().size(), order.getOrderItems().size());
	}
	
	@Test
	public void testToEntity() {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setUserDTO(new UserDTO());
		List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
		orderItemsDTO.add(new OrderItemDTO());
		orderItemsDTO.add(new OrderItemDTO());
		orderDTO.setOrderItemsDTO(orderItemsDTO);
		
		Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
		
		assertNotNull(order);
		assertEquals(order.getId(), orderDTO.getId());
		assertEquals(order.getOrderDate(), orderDTO.getOrderDate());
		assertNotNull(order.getUser());
		assertNotNull(order.getOrderItems());
		assertEquals(order.getOrderItems().size(), orderDTO.getOrderItemsDTO().size());
	}
}
