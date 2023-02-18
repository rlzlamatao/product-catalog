package productcatalog.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import productcatalog.data.Order;
import productcatalog.data.OrderItem;
import productcatalog.data.Product;
import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.ProductDTO;

public class OrderItemMapperTest {
	
	@Test
 	public void testToDTO() {
		
		OrderItem orderItem = new OrderItem();
		orderItem.setId(1L);
		orderItem.setOrder(new Order());
		orderItem.setProduct(new Product());
		orderItem.setQuantity(3);
		
		OrderItemDTO orderItemDTO = OrderItemMapper.INSTANCE.toDTO(orderItem);
		
		assertNotNull(orderItemDTO);
		assertEquals(orderItemDTO.getId(), orderItem.getId());
		assertNotNull(orderItemDTO.getOrderDTO());
		assertNull(orderItemDTO.getOrderDTO().getOrderItemsDTO());
		assertNotNull(orderItemDTO.getProductDTO());
		assertEquals(orderItemDTO.getQuantity(), orderItem.getQuantity());
	}
	
	@Test
	public void testToEntity() {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(1L);
		orderItemDTO.setOrderDTO(new OrderDTO());
		orderItemDTO.setProductDTO(new ProductDTO());
		orderItemDTO.setQuantity(3);
		
		OrderItem orderItem = OrderItemMapper.INSTANCE.toEntity(orderItemDTO);
		
		assertNotNull(orderItem);
		assertEquals(orderItem.getId(), orderItemDTO.getId());
		assertNotNull(orderItem.getOrder());
		assertNull(orderItem.getOrder().getOrderItems());
		assertNotNull(orderItem.getProduct());
		assertEquals(orderItem.getQuantity(), orderItemDTO.getQuantity());
	}
	

}
