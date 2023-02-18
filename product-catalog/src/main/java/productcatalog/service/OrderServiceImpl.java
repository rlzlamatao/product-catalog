package productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalog.data.Order;
import productcatalog.dto.OrderDTO;
import productcatalog.mapper.OrderItemMapper;
import productcatalog.mapper.OrderMapper;
import productcatalog.mapper.UserMapper;
import productcatalog.repository.OrderRepository;
import productcatalog.service.interfaces.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public OrderDTO saveOrder(OrderDTO orderDTO) {
		Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
		Order savedOrder = orderRepository.save(order);
		OrderDTO savedOrderDTO = OrderMapper.INSTANCE.toDTO(savedOrder);
		return savedOrderDTO;
	}

	@Override
	public OrderDTO getOrder(Long id) {
		Order order = orderRepository.findById(id).orElseThrow();
		OrderDTO foundOrderDTO = OrderMapper.INSTANCE.toDTO(order);
		return foundOrderDTO;
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		List<OrderDTO> ordersDTO = orders.stream().map(OrderMapper.INSTANCE::toDTO).collect(Collectors.toList());
		return ordersDTO;
	}

	@Override
	public OrderDTO updateOrder(Long id, OrderDTO newOrderDTO) {
		Order order =  orderRepository.findById(id)
				.map(o -> {
					o.setOrderDate(newOrderDTO.getOrderDate());
					o.setOrderItems(newOrderDTO.getOrderItemsDTO()
									.stream()
									.map(OrderItemMapper.INSTANCE::toEntity)
									.collect(Collectors.toList()));
					o.setUser(UserMapper.INSTANCE.toEntity(newOrderDTO.getUserDTO()));
					return orderRepository.save(o);
				}).orElse(null);
		OrderDTO updatedOrderDTO = OrderMapper.INSTANCE.toDTO(order);
		return updatedOrderDTO;
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}

}
