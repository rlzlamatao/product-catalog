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

import productcatalog.dto.CategoryDTO;
import productcatalog.dto.ProductDTO;
import productcatalog.service.interfaces.CategoryService;

@WebMvcTest(CategoryAPI.class)
public class CategoryAPITest {

	@MockBean
	private CategoryService categoryService;
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testCreateCategory() throws Exception {

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("category name");
		categoryDTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));

		when(categoryService.saveCategory(categoryDTO)).thenReturn(categoryDTO);

		mvc.perform(post("/category")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoryDTO)))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(jsonPath("$.name", is("category name")))
					.andExpect(jsonPath("$.productsDTO", hasSize(2)));

		verify(categoryService, times(1)).saveCategory(categoryDTO);
	}
	
	@Test
	public void testGetCategory() throws Exception {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("category name");
		categoryDTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));
		
		when(categoryService.getCategory(1L)).thenReturn(categoryDTO);
		
		mvc.perform(get("/category/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("category name")))
				.andExpect(jsonPath("$.productsDTO", hasSize(2)));
		
		verify(categoryService, times(1)).getCategory(1L);
			
	}

	@Test
	public void testGetAllCategories() throws Exception {
		
		CategoryDTO category1DTO = new CategoryDTO();
		category1DTO.setId(1L);
		category1DTO.setName("category1 name");
		category1DTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));
		CategoryDTO category2DTO = new CategoryDTO();
		category2DTO.setId(1L);
		category2DTO.setName("category2 name");
		category2DTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));

		List<CategoryDTO> categoriesDTO = Arrays.asList(category1DTO, category2DTO);

		when(categoryService.getAllCategories()).thenReturn(categoriesDTO);

		mvc.perform(get("/category"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].name", is("category1 name")))
			.andExpect(jsonPath("$[0].productsDTO", hasSize(2)))
			.andExpect(jsonPath("$[1].name", is("category2 name")))
			.andExpect(jsonPath("$[1].productsDTO", hasSize(2)));

		verify(categoryService, times(1)).getAllCategories();
	}

	@Test
	public void testUpdateCategory() throws Exception {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("category name");
		categoryDTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));

		when(categoryService.updateCategory(1L, categoryDTO)).thenReturn(categoryDTO);

		mvc.perform(put("/category/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(categoryDTO)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id", is(1)))
					.andExpect(jsonPath("$.name", is("category name")))
					.andExpect(jsonPath("$.productsDTO", hasSize(2)));

		verify(categoryService, times(1)).updateCategory(1L, categoryDTO);
	}

	@Test
	public void testDeleteCategory() throws Exception {
		
		Long id = 1L;

		mvc.perform(delete("/category/{id}", id))
			.andExpect(status().isNoContent());

		verify(categoryService, times(1)).deleteCategory(id);
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
