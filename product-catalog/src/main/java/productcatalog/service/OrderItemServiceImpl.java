package productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalog.data.OrderItem;
import productcatalog.dto.OrderItemDTO;
import productcatalog.mapper.OrderItemMapper;
import productcatalog.mapper.OrderMapper;
import productcatalog.mapper.ProductMapper;
import productcatalog.repository.OrderItemRepository;
import productcatalog.service.interfaces.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO) {

		OrderItem orderItem = OrderItemMapper.INSTANCE.toEntity(orderItemDTO);
		OrderItem savedOrderItem = orderItemRepository.save(orderItem);
		OrderItemDTO savedOrderItemDTO = OrderItemMapper.INSTANCE.toDTO(savedOrderItem);

		return savedOrderItemDTO;
	}

	@Override
	public OrderItemDTO getOrderItem(Long id) {

		OrderItem foundOrderItem = orderItemRepository.findById(id).orElseThrow();
		OrderItemDTO foundOrderItemDTO = OrderItemMapper.INSTANCE.toDTO(foundOrderItem);

		return foundOrderItemDTO;
	}

	@Override
	public List<OrderItemDTO> getAllOrderItems() {

		List<OrderItem> orderItems = orderItemRepository.findAll();
		List<OrderItemDTO> orderItemsDTO = orderItems.stream().map(OrderItemMapper.INSTANCE::toDTO)
				.collect(Collectors.toList());

		return orderItemsDTO;
	}

	@Override
	public OrderItemDTO updateOrderItem(Long id, OrderItemDTO newOrderItemDTO) {

		OrderItem orderItem = orderItemRepository.findById(id).map(o -> {
			o.setOrder(OrderMapper.INSTANCE.toEntity(newOrderItemDTO.getOrderDTO()));
			o.setProduct(ProductMapper.INSTANCE.toEntity(newOrderItemDTO.getProductDTO()));
			o.setQuantity(newOrderItemDTO.getQuantity());
			return orderItemRepository.save(o);
		}).orElse(null);
		OrderItemDTO orderItemDTO = OrderItemMapper.INSTANCE.toDTO(orderItem);

		return orderItemDTO;
	}

	@Override
	public void deleteOrderItem(Long id) {
		orderItemRepository.deleteById(id);
	}

}
