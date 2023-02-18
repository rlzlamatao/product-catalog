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
import productcatalog.dto.OrderDTO;
import productcatalog.service.interfaces.OrderService;

@RestController
@RequestMapping("/order")
public class OrderAPI {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderAPI(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
		return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
		return new ResponseEntity<>(orderService.saveOrder(orderDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
		return new ResponseEntity<>(orderService.updateOrder(id, orderDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
