package productcatalog.controller.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.ProductDTO;
import productcatalog.service.interfaces.OrderItemService;

@WebMvcTest(OrderItemAPI.class)
public class OrderItemAPITest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private OrderItemService orderItemService;
	
	@Test
	public void testCreateOrderItem() throws Exception {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(1L);
		orderItemDTO.setOrderDTO(new OrderDTO());
		orderItemDTO.setProductDTO(new ProductDTO());
		orderItemDTO.setQuantity(3);
		
		when(orderItemService.saveOrderItem(orderItemDTO)).thenReturn(orderItemDTO);
		
		mvc.perform(post("/orderItem")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(orderItemDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.orderDTO", is(notNullValue())))
				.andExpect(jsonPath("$.productDTO", is(notNullValue())))
				.andExpect(jsonPath("$.quantity", is(3)));
		
		verify(orderItemService, times(1)).saveOrderItem(orderItemDTO);
	}
	
	@Test
	public void testUpdateOrderItem() throws Exception {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(1L);
		orderItemDTO.setOrderDTO(new OrderDTO());
		orderItemDTO.setProductDTO(new ProductDTO());
		orderItemDTO.setQuantity(3);
		
		when(orderItemService.updateOrderItem(1L, orderItemDTO)).thenReturn(orderItemDTO);
		
		mvc.perform(put("/orderItem/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(orderItemDTO)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(jsonPath("$.orderDTO", is(notNullValue())))
					.andExpect(jsonPath("$.productDTO", is(notNullValue())))
					.andExpect(jsonPath("$.quantity", is(3)));
		
		verify(orderItemService, times(1)).updateOrderItem(1L, orderItemDTO);
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	public void testGetOrderItem() throws Exception {
		
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(1L);
		orderItemDTO.setOrderDTO(new OrderDTO());
		orderItemDTO.setProductDTO(new ProductDTO());
		orderItemDTO.setQuantity(3);
		
		when(orderItemService.getOrderItem(1L)).thenReturn(orderItemDTO);
		
		mvc.perform(get("/orderItem/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(orderItemDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.orderDTO", is(notNullValue())))
				.andExpect(jsonPath("$.productDTO", is(notNullValue())))
				.andExpect(jsonPath("$.quantity", is(3)));
		
		verify(orderItemService, times(1)).getOrderItem(1L);
	}
	
	@Test
	public void testDeleteOrderItem() throws Exception {
		
		mvc.perform(delete("/orderItem/{id}", 1L))
			.andExpect(status().isNoContent());
		
		verify(orderItemService, times(1)).deleteOrderItem(1L);
	}
	
	@Test
	public void testGetAllOrderItems() throws Exception {
		
		OrderItemDTO orderItem1DTO = new OrderItemDTO();
		orderItem1DTO.setId(1L);
		orderItem1DTO.setOrderDTO(new OrderDTO());	
		orderItem1DTO.setProductDTO(new ProductDTO());
		orderItem1DTO.setQuantity(3);
		OrderItemDTO orderItem2DTO = new OrderItemDTO();
		orderItem2DTO.setId(2L);
		orderItem2DTO.setOrderDTO(new OrderDTO());
		orderItem2DTO.setProductDTO(new ProductDTO());
		orderItem2DTO.setQuantity(5);
		
		List<OrderItemDTO> orderItemsDTO = Arrays.asList(orderItem1DTO, orderItem2DTO);
		
		when(orderItemService.getAllOrderItems()).thenReturn(orderItemsDTO);
		
		mvc.perform(get("/orderItem"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].orderDTO", is(notNullValue())))
			.andExpect(jsonPath("$[0].productDTO", is(notNullValue())))
			.andExpect(jsonPath("$[0].quantity", is(3)))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].orderDTO", is(notNullValue())))
			.andExpect(jsonPath("$[1].productDTO", is(notNullValue())))
			.andExpect(jsonPath("$[1].quantity", is(5)));
		
		verify(orderItemService, times(1)).getAllOrderItems();
	}
}
