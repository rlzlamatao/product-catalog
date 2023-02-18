package productcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import productcatalog.data.Category;
import productcatalog.data.Product;
import productcatalog.dto.CategoryDTO;
import productcatalog.dto.ProductDTO;
import productcatalog.mapper.CategoryMapper;
import productcatalog.repository.CategoryRepository;
import productcatalog.service.interfaces.CategoryService;

@SpringBootTest
public class CategoryServiceImplTest {
	
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;

	
	@Test
	public void testSaveCategory() {
		// Prepare the input CategoryDTO
	    CategoryDTO categoryDTO = new CategoryDTO();
	    categoryDTO.setName("Test Category");
	    Category savedCategory = CategoryMapper.INSTANCE.toEntity(categoryDTO);
	    
	    // When the save method is called on the mock, return a Category object
	    when(categoryRepository.save(savedCategory)).thenReturn(savedCategory);
	    
	    // Call the saveCategory method
	    CategoryDTO savedCategoryDTO = categoryService.saveCategory(categoryDTO);
	    
	    // Verify that the save method of the CategoryRepository was called with the correct Category object
	    verify(categoryRepository, times(1)).save(CategoryMapper.INSTANCE.toEntity(categoryDTO));
	    
	    // Assert that the returned CategoryDTO is correct
	    assertEquals(categoryDTO.getName(), savedCategoryDTO.getName());
	}
	
	@Test
	public void testGetCategory() {
		
		Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));

        CategoryDTO categoryDTO = categoryService.getCategory(1L);
        
        verify(categoryRepository, times(1)).findById(1L);

        assertEquals(1L, categoryDTO.getId());
        assertEquals("Test Category", categoryDTO.getName());
	}
	
	@Test
	public void testGetAllCategories() {
		
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("Test Category1");
		Category category2 = new Category();
		category2.setId(2L);
		category2.setName("Test Category2");
		List<Category> categories = Arrays.asList(category1, category2);
		
		when(categoryRepository.findAll()).thenReturn(categories);
		
		List<CategoryDTO> categoriesDTO = categoryService.getAllCategories();
		
		verify(categoryRepository, times(1)).findAll();
		
		assertEquals(categoriesDTO.size(), 2);
		assertEquals(categoriesDTO.get(0).getId(), category1.getId());
		assertEquals(categoriesDTO.get(0).getName(), category1.getName());
		assertEquals(categoriesDTO.get(1).getId(), category2.getId());
		assertEquals(categoriesDTO.get(1).getName(), category2.getName());
	}
	
	@Test
	public void testUpdateCategory() {
		
		Long id = 1L;
	    CategoryDTO newCategoryDTO = new CategoryDTO();
	    newCategoryDTO.setId(id);
	    newCategoryDTO.setName("New Test Category");
	    newCategoryDTO.setProductsDTO(Arrays.asList(new ProductDTO(), new ProductDTO()));
	    Category category = new Category();
	    category.setId(id);
	    category.setName("Test Category");
	    category.setProducts(Arrays.asList(new Product()));
	    
	    when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
	    when(categoryRepository.save(category)).thenReturn(category);
	    
	    CategoryDTO updatedCategoryDTO = categoryService.updateCategory(1L, newCategoryDTO);
	    
	    verify(categoryRepository, times(1)).findById(id);
	    verify(categoryRepository, times(1)).save(category);
	    
	    assertEquals(newCategoryDTO.getId(), updatedCategoryDTO.getId());
	    assertEquals(newCategoryDTO.getName(), updatedCategoryDTO.getName());
	    assertEquals(newCategoryDTO.getProductsDTO().size(), updatedCategoryDTO.getProductsDTO().size());
	}
	
	@Test
	public void testDeleteCategory() {
		
		Long id = 1L;
		
		categoryService.deleteCategory(id);
		
		verify(categoryRepository, times(1)).deleteById(id);
	}
	

}
