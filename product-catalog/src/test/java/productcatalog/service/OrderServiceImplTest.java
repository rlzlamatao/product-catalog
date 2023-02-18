package productcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import productcatalog.data.Order;
import productcatalog.data.OrderItem;
import productcatalog.data.User;
import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.UserDTO;
import productcatalog.mapper.OrderMapper;
import productcatalog.repository.OrderRepository;
import productcatalog.service.interfaces.OrderService;

@SpringBootTest
public class OrderServiceImplTest {
	
	@MockBean
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderService orderService;
	
	
	@Test
	public void testSaveOrder() {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setUserDTO(new UserDTO());
		List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
		orderItemsDTO.add(new OrderItemDTO());
		orderItemsDTO.add(new OrderItemDTO());
		orderDTO.setOrderItemsDTO(orderItemsDTO);
		
		Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
		Order savedOrder = order;
		
		when(orderRepository.save(order)).thenReturn(savedOrder);
		
		OrderDTO savedOrderDTO = orderService.saveOrder(orderDTO);
		
		verify(orderRepository, times(1)).save(order);
		
		assertNotNull(savedOrderDTO);
		assertEquals(savedOrderDTO.getId(), orderDTO.getId());
		assertEquals(savedOrderDTO.getOrderDate(), orderDTO.getOrderDate());
		assertNotNull(savedOrderDTO.getUserDTO());
		assertNotNull(savedOrderDTO.getOrderItemsDTO());
		assertEquals(savedOrderDTO.getOrderItemsDTO().size(), order.getOrderItems().size());
	}
	
	@Test
	public void testGetOrder() {
		
		Long id = 1L;
		
		Order order = new Order();
		order.setId(id);
		order.setOrderDate(new Date());
		order.setUser(new User());
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		order.setOrderItems(orderItems);
		
		when(orderRepository.findById(id)).thenReturn(Optional.of(order));
		
		OrderDTO orderDTO = orderService.getOrder(id);
		
		verify(orderRepository, times(1)).findById(id);
		
		assertNotNull(orderDTO);
		assertEquals(orderDTO.getId(), order.getId());
		assertEquals(orderDTO.getOrderDate(), order.getOrderDate());
		assertNotNull(orderDTO.getUserDTO());
		assertNotNull(orderDTO.getOrderItemsDTO());
		assertEquals(orderDTO.getOrderItemsDTO().size(), order.getOrderItems().size());
	}
	
	@Test
	public void testGetAllOrders() {
		
		Order order1 = new Order();
		order1.setId(1L);
		order1.setOrderDate(new Date());
		order1.setUser(new User());
		List<OrderItem> orderItems1 = new ArrayList<>();
		orderItems1.add(new OrderItem());
		orderItems1.add(new OrderItem());
		order1.setOrderItems(orderItems1);
		Order order2 = new Order();
		order2.setId(2L);
		order2.setOrderDate(new Date());
		order2.setUser(new User());
		List<OrderItem> orderItems2 = new ArrayList<>();
		orderItems2.add(new OrderItem());
		orderItems2.add(new OrderItem());
		order2.setOrderItems(orderItems2);
		
		List<Order> orders = Arrays.asList(order1, order2);
		
		when(orderRepository.findAll()).thenReturn(orders);
		
		List<OrderDTO> ordersDTO = orderService.getAllOrders();
		
		verify(orderRepository, times(1)).findAll();
		
		assertNotNull(ordersDTO);
		assertEquals(ordersDTO.size(), orders.size());
		
		assertEquals(ordersDTO.get(0).getId(), orders.get(0).getId());
		assertEquals(ordersDTO.get(0).getOrderDate(), orders.get(0).getOrderDate());
		assertNotNull(ordersDTO.get(0).getUserDTO());
		assertNotNull(ordersDTO.get(0).getOrderItemsDTO());
		assertEquals(ordersDTO.get(0).getOrderItemsDTO().size(), orders.get(0).getOrderItems().size());
		
		assertEquals(ordersDTO.get(1).getId(), orders.get(1).getId());
		assertEquals(ordersDTO.get(1).getOrderDate(), orders.get(1).getOrderDate());
		assertNotNull(ordersDTO.get(1).getUserDTO());
		assertNotNull(ordersDTO.get(1).getOrderItemsDTO());
		assertEquals(ordersDTO.get(1).getOrderItemsDTO().size(), orders.get(1).getOrderItems().size());
	}
	
	@Test
	public void testUpdateOrder() {
		
		Long id = 1L;
		
		OrderDTO newOrderDTO = new OrderDTO();
		newOrderDTO.setId(id);
		newOrderDTO.setOrderDate(new Date());
		newOrderDTO.setUserDTO(new UserDTO());
		List<OrderItemDTO> newOrderItemsDTO = new ArrayList<>();
		newOrderItemsDTO.add(new OrderItemDTO());
		newOrderItemsDTO.add(new OrderItemDTO());
		newOrderDTO.setOrderItemsDTO(newOrderItemsDTO);
		
		Order order = new Order();
		order.setId(id);
		order.setOrderDate(new Date());
		order.setUser(new User());
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(new OrderItem());
		orderItems.add(new OrderItem());
		order.setOrderItems(orderItems);
		
		when(orderRepository.findById(id)).thenReturn(Optional.of(order));
		when(orderRepository.save(order)).thenReturn(order);
		
		OrderDTO updatedOrderDTO = orderService.updateOrder(id, newOrderDTO);
		
		verify(orderRepository, times(1)).findById(id);
		verify(orderRepository, times(1)).save(order);
		
		assertNotNull(updatedOrderDTO);
		assertEquals(updatedOrderDTO.getId(), newOrderDTO.getId());
		assertEquals(updatedOrderDTO.getOrderDate(), newOrderDTO.getOrderDate());
		assertNotNull(updatedOrderDTO.getUserDTO());
		assertNotNull(updatedOrderDTO.getOrderItemsDTO());
		assertEquals(updatedOrderDTO.getOrderItemsDTO().size(), newOrderDTO.getOrderItemsDTO().size());
	}
	
	@Test
 	public void testDeleteOrder() {
		
		Long id = 1L;
		
		orderService.deleteOrder(id);
		
		verify(orderRepository, times(1)).deleteById(id);
	}
	
}
