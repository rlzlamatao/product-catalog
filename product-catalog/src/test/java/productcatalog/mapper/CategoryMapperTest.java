package productcatalog.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import productcatalog.data.Category;
import productcatalog.data.Product;
import productcatalog.dto.CategoryDTO;
import productcatalog.dto.ProductDTO;

public class CategoryMapperTest {

	@Test
	public void testToDTO() {

		Category category = new Category();
		category.setId(1L);
		category.setName("categoryDTOName");
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		category.setProducts(products);

		CategoryDTO categoryDTO = CategoryMapper.INSTANCE.toDTO(category);

		assertNotNull(categoryDTO);
		assertEquals(categoryDTO.getId(), category.getId());
		assertEquals(categoryDTO.getName(), category.getName());
		assertNotNull(categoryDTO.getProductsDTO());
		assertEquals(categoryDTO.getProductsDTO().size(), category.getProducts().size());
	}

	@Test
	public void testToDTOWithoutProductsDTO() {
		
		Category category = new Category();
		category.setId(1L);
		category.setName("categoryDTOName");
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		category.setProducts(products);
		
		CategoryDTO categoryDTO = CategoryMapper.INSTANCE.toDTOWithoutProductsDTO(category);
		
		assertNotNull(categoryDTO);
		assertEquals(categoryDTO.getId(), category.getId());
		assertEquals(categoryDTO.getName(), category.getName());
		assertNull(categoryDTO.getProductsDTO());
	}
	
	@Test
	public void testToEntity() {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("categoryEntityName");
		List<ProductDTO> productsDTO = new ArrayList<>();
		productsDTO.add(new ProductDTO());
		productsDTO.add(new ProductDTO());
		categoryDTO.setProductsDTO(productsDTO);
		
		Category category = CategoryMapper.INSTANCE.toEntity(categoryDTO);
		
		assertNotNull(category);
		assertEquals(category.getId(), categoryDTO.getId());
		assertEquals(category.getName(), categoryDTO.getName());
		assertNotNull(category.getProducts());
		assertEquals(category.getProducts().size(), categoryDTO.getProductsDTO().size());
	}
	
	@Test
	public void testToEntityWithoutProducts() {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("categoryEntityName");
		List<ProductDTO> productsDTO = new ArrayList<>();
		productsDTO.add(new ProductDTO());
		productsDTO.add(new ProductDTO());
		
		Category category = CategoryMapper.INSTANCE.toEntityWithoutProducts(categoryDTO);
		
		assertNotNull(category);
		assertEquals(category.getId(), categoryDTO.getId());
		assertEquals(category.getName(), categoryDTO.getName());
		assertNull(category.getProducts());
	}
}
