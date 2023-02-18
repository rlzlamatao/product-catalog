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

import productcatalog.data.Order;
import productcatalog.data.OrderItem;
import productcatalog.data.Product;
import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.ProductDTO;
import productcatalog.mapper.OrderItemMapper;
import productcatalog.repository.OrderItemRepository;
import productcatalog.service.interfaces.OrderItemService;

@SpringBootTest
public class OrderItemServiceImplTest {
	
	@MockBean
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemService orderItemService;

	
	
	@Test
	public void testSaveOrderItem() {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(1L);
		orderItemDTO.setOrderDTO(new OrderDTO());
		orderItemDTO.setProductDTO(new ProductDTO());
		orderItemDTO.setQuantity(5);
		
		OrderItem orderItem = OrderItemMapper.INSTANCE.toEntity(orderItemDTO);
		OrderItem savedOrderItem = orderItem;
		
		when(orderItemRepository.save(orderItem)).thenReturn(savedOrderItem);
		
		OrderItemDTO savedOrderItemDTO = orderItemService.saveOrderItem(orderItemDTO);
		
		verify(orderItemRepository, times(1)).save(orderItem);
		
		assertNotNull(savedOrderItemDTO);
		assertNotNull(savedOrderItemDTO.getOrderDTO());
		assertNotNull(savedOrderItemDTO.getProductDTO());
		
		assertEquals(savedOrderItemDTO.getId(), orderItemDTO.getId());
		assertEquals(savedOrderItemDTO.getQuantity(), orderItemDTO.getQuantity());
	}
	
	@Test
	public void testGetOrderItem() {
		
		Long id = 1L;
		
		OrderItem foundOrderItem = new OrderItem();
		foundOrderItem.setId(1L);
		foundOrderItem.setOrder(new Order());
		foundOrderItem.setProduct(new Product());
		foundOrderItem.setQuantity(5);
		
		when(orderItemRepository.findById(id)).thenReturn(Optional.of(foundOrderItem));
		
		OrderItemDTO foundOrderItemDTO = orderItemService.getOrderItem(id);
		
		verify(orderItemRepository, times(1)).findById(id);
		
		assertNotNull(foundOrderItemDTO);
		assertNotNull(foundOrderItemDTO.getOrderDTO());
		assertNotNull(foundOrderItemDTO.getProductDTO());
		
		assertEquals(foundOrderItemDTO.getId(), foundOrderItem.getId());
		assertEquals(foundOrderItemDTO.getQuantity(), foundOrderItem.getQuantity());
	}
	
	@Test
	public void testGetAllOrderItem() {
		
		OrderItem orderItem1 = new OrderItem();
		orderItem1.setId(1L);
		orderItem1.setOrder(new Order());
		orderItem1.setProduct(new Product());
		orderItem1.setQuantity(5);
		OrderItem orderItem2 = new OrderItem();
		orderItem2.setId(1L);
		orderItem2.setOrder(new Order());
		orderItem2.setProduct(new Product());
		orderItem2.setQuantity(5);
		
		List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
		
		when(orderItemRepository.findAll()).thenReturn(orderItems);
		
		List<OrderItemDTO> orderItemsDTO = orderItemService.getAllOrderItems();
		
		assertNotNull(orderItemsDTO);
		assertNotNull(orderItemsDTO.get(0).getOrderDTO());
		assertNotNull(orderItemsDTO.get(0).getProductDTO());
		assertNotNull(orderItemsDTO.get(1).getOrderDTO());
		assertNotNull(orderItemsDTO.get(1).getProductDTO());
		
		assertEquals(orderItemsDTO.size(), orderItems.size());
		assertEquals(orderItemsDTO.get(0).getId(), orderItems.get(0).getId());
		assertEquals(orderItemsDTO.get(0).getQuantity(), orderItems.get(0).getQuantity());
		assertEquals(orderItemsDTO.get(1).getId(), orderItems.get(1).getId());
		assertEquals(orderItemsDTO.get(1).getQuantity(), orderItems.get(1).getQuantity());
	}
	
	@Test
	public void testUpdateOrderItem() {
		
		Long id = 1L;
		
		OrderItemDTO newOrderItemDTO = new OrderItemDTO();
		newOrderItemDTO.setId(id);
		newOrderItemDTO.setOrderDTO(new OrderDTO());
		newOrderItemDTO.setProductDTO(new ProductDTO());
		newOrderItemDTO.setQuantity(10);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setId(id);
		orderItem.setOrder(new Order());
		orderItem.setProduct(new Product());
		orderItem.setQuantity(5);
		
		when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));
		when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
		
		OrderItemDTO updatedOrderItemDTO = orderItemService.updateOrderItem(id, newOrderItemDTO);
		
		verify(orderItemRepository, times(1)).findById(id);
		verify(orderItemRepository, times(1)).save(orderItem);
		
		assertNotNull(updatedOrderItemDTO);
		assertNotNull(updatedOrderItemDTO.getOrderDTO());
		assertNotNull(updatedOrderItemDTO.getProductDTO());
		
		assertEquals(updatedOrderItemDTO.getId(), newOrderItemDTO.getId());
		assertEquals(updatedOrderItemDTO.getQuantity(), newOrderItemDTO.getQuantity());
	}
	
	@Test
	public void testDeleteOrderItem() {
		
		Long id = 1L;
		
		orderItemService.deleteOrderItem(id);
		
		verify(orderItemRepository, times(1)).deleteById(id);
	}

}
