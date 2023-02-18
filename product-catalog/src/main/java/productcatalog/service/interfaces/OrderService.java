package productcatalog.service.interfaces;

import java.util.List;

import productcatalog.dto.OrderDTO;

public interface OrderService {
	
	OrderDTO saveOrder(OrderDTO orderDTO);
	OrderDTO getOrder(Long id);
	List<OrderDTO> getAllOrders();
	OrderDTO updateOrder(Long id, OrderDTO newOrder);
	void deleteOrder(Long id);
	
}
