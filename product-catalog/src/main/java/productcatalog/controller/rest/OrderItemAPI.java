package productcatalog.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import productcatalog.dto.OrderItemDTO;
import productcatalog.service.interfaces.OrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemAPI {

	private final OrderItemService orderItemService;

	@Autowired
	public OrderItemAPI(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
		return new ResponseEntity<>(orderItemService.getAllOrderItems(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
		return new ResponseEntity<>(orderItemService.getOrderItem(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
		return new ResponseEntity<>(orderItemService.saveOrderItem(orderItemDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItemDTO orderItemDTO) {
		return new ResponseEntity<>(orderItemService.updateOrderItem(id, orderItemDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
		orderItemService.deleteOrderItem(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
