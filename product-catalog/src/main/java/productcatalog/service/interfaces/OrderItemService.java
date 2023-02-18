package productcatalog.service.interfaces;

import java.util.List;

import productcatalog.dto.OrderItemDTO;

public interface OrderItemService {

	OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO);

	OrderItemDTO getOrderItem(Long id);

	List<OrderItemDTO> getAllOrderItems();

	OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);

	void deleteOrderItem(Long id);

}
