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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import productcatalog.dto.OrderDTO;
import productcatalog.dto.OrderItemDTO;
import productcatalog.dto.UserDTO;
import productcatalog.service.interfaces.OrderService;


@WebMvcTest(OrderAPI.class)
public class OrderAPITest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private OrderService orderService;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	Date date;
	
	private String getDate() {
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = new Date(calendar.getTimeInMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		return formatter.format(date);
	}
	
	
	@Test
	public void testCreateOrder() throws Exception {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setUserDTO(new UserDTO());
		orderDTO.setOrderItemsDTO(Arrays.asList(new OrderItemDTO(), new OrderItemDTO()));
		
		when(orderService.saveOrder(orderDTO)).thenReturn(orderDTO);
		
		mvc.perform(post("/order")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(orderDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
			    .andExpect(jsonPath("$.orderDate", isDate(getDate()))) 
				.andExpect(jsonPath("$.userDTO", is(notNullValue())))
				.andExpect(jsonPath("$.orderItemsDTO", hasSize(2)));
		
		verify(orderService, times(1)).saveOrder(orderDTO);
		
	}
	
	@Test
	public void testGetAllOrders() throws Exception {

		OrderDTO order1DTO = new OrderDTO();
		order1DTO.setId(1L);
		order1DTO.setOrderDate(new Date());
		order1DTO.setUserDTO(new UserDTO());
		order1DTO.setOrderItemsDTO(Arrays.asList(new OrderItemDTO(), new OrderItemDTO()));
		OrderDTO order2DTO = new OrderDTO();
		order2DTO.setId(2L);
		order2DTO.setOrderDate(new Date());
		order2DTO.setUserDTO(new UserDTO());
		order2DTO.setOrderItemsDTO(Arrays.asList(new OrderItemDTO(), new OrderItemDTO()));

		List<OrderDTO> ordersDTO = Arrays.asList(order1DTO, order2DTO);

		when(orderService.getAllOrders()).thenReturn(ordersDTO);

		mvc.perform(get("/order"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].orderDate", isDate(getDate())))
			.andExpect(jsonPath("$[0].userDTO", is(notNullValue())))
			.andExpect(jsonPath("$[0].orderItemsDTO", hasSize(2)))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].orderDate", isDate(getDate())))
			.andExpect(jsonPath("$[1].userDTO", is(notNullValue())))
			.andExpect(jsonPath("$[1].orderItemsDTO", hasSize(2)));

		verify(orderService, times(1)).getAllOrders();
	}
	
	@Test
	public void testUpdateOrder() throws Exception {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setUserDTO(new UserDTO());
		orderDTO.setOrderItemsDTO(Arrays.asList(new OrderItemDTO(), new OrderItemDTO()));
		
		when(orderService.updateOrder(1L, orderDTO)).thenReturn(orderDTO);
		
		mvc.perform(put("/order/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(orderDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.orderDate", isDate(getDate())))
				.andExpect(jsonPath("$.userDTO", is(notNullValue())))
				.andExpect(jsonPath("$.orderItemsDTO", hasSize(2)));
		
		verify(orderService, times(1)).updateOrder(1L, orderDTO);
	}
	
	@Test
	public void testGetOrder() throws Exception {
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setUserDTO(new UserDTO());
		orderDTO.setOrderItemsDTO(Arrays.asList(new OrderItemDTO(), new OrderItemDTO()));
		
		when(orderService.getOrder(1L)).thenReturn(orderDTO);
		
		mvc.perform(get("/order/{id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.orderDate", isDate(getDate())))
			.andExpect(jsonPath("$.userDTO", is(notNullValue())))
			.andExpect(jsonPath("$.orderItemsDTO", hasSize(2)));
		
		verify(orderService, times(1)).getOrder(1L);
		
	}
	
	@Test
	public void testDeleteOrder() throws Exception {
		
		Long id = 1L;
		
		mvc.perform(delete("/order/{id}", 1L))
			.andExpect(status().isNoContent());
		
		verify(orderService, times(1)).deleteOrder(id);
	}
		 
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private static Matcher<String> isDate(final String date) {
	    return new TypeSafeMatcher<String>() {

	        @Override
	        protected boolean matchesSafely(String item) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            try {
	                Date actualDate = dateFormat.parse(item);
	                Date expectedDate = dateFormat.parse(date);
	                return actualDate.equals(expectedDate);
	            } catch (ParseException e) {
	                return false;
	            }
	        }

	        @Override
	        public void describeTo(Description description) {
	            description.appendText("a string representation of date in yyyy-MM-dd format");
	        }

	    };
	}

}
