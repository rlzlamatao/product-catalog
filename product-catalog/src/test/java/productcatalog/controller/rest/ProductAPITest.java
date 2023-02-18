package productcatalog.controller.rest;

import static org.hamcrest.CoreMatchers.is;
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

import productcatalog.dto.ProductDTO;
import productcatalog.service.interfaces.ProductService;


@WebMvcTest(ProductAPI.class)
public class ProductAPITest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void testCreateProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("product name");
		productDTO.setDescription("product description");
		productDTO.setPrice(10.0);
		
		when(productService.saveProduct(productDTO)).thenReturn(productDTO);
		
		mvc.perform(post("/product")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(productDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("product name")))
				.andExpect(jsonPath("$.description", is("product description")))
				.andExpect(jsonPath("$.price", is(10.0)));
		
		verify(productService, times(1)).saveProduct(productDTO);
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("product name");
		productDTO.setDescription("product description");
		productDTO.setPrice(10.0);
		
		when(productService.updateProduct(1L, productDTO)).thenReturn(productDTO);
		
		mvc.perform(put("/product/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(productDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("product name")))
				.andExpect(jsonPath("$.description", is("product description")))
				.andExpect(jsonPath("$.price", is(10.0)));
		
		verify(productService, times(1)).updateProduct(1L, productDTO);
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		Long id = 1L;
		
		mvc.perform(delete("/product/{id}", id))
			.andExpect(status().isNoContent());
		
		verify(productService, times(1)).deleteProduct(id);
	}
	
	@Test
	public void testGetProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("product name");
		productDTO.setDescription("product description");
		productDTO.setPrice(10.0);
		
		when(productService.getProduct(1L)).thenReturn(productDTO);
	
		mvc.perform(get("/product/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("product name")))
			.andExpect(jsonPath("$.description", is("product description")))
			.andExpect(jsonPath("$.price", is(10.0)));
		
		verify(productService, times(1)).getProduct(1L);
	}
	
	
	@Test
	public void testGetAllProducts() throws Exception {
		ProductDTO productDTO1 = new ProductDTO();
		productDTO1.setName("product1 name");
		productDTO1.setDescription("product1 description");
		productDTO1.setPrice(10.0);
		ProductDTO productDTO2 = new ProductDTO();
		productDTO2.setName("product2 name");
		productDTO2.setDescription("product2 description");
		productDTO2.setPrice(20.0);
		
		List<ProductDTO> productsDTO = Arrays.asList(productDTO1, productDTO2);
		
		when(productService.getAllProducts()).thenReturn(productsDTO);
		
		mvc.perform(get("/product"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].name", is("product1 name")))
			.andExpect(jsonPath("$[0].description", is("product1 description")))
			.andExpect(jsonPath("$[0].price", is(10.0)))
			.andExpect(jsonPath("$[1].name", is("product2 name")))
			.andExpect(jsonPath("$[1].description", is("product2 description")))
			.andExpect(jsonPath("$[1].price", is(20.0)));		
		
		verify(productService, times(1)).getAllProducts();
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
