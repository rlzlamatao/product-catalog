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

public class ProductMapperTest {

	@Test
	public void testToDTO() {

		Product product = new Product();
		product.setId(1L);
		product.setName("productName");
		product.setDescription("productDescription");
		product.setPrice(10.0);

		Category category = new Category();
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		category.setProducts(products);
		product.setCategory(category);

		ProductDTO productDTO = ProductMapper.INSTANCE.toDTO(product);

		assertNotNull(productDTO);
		assertEquals(productDTO.getId(), product.getId());
		assertEquals(productDTO.getName(), product.getName());
		assertEquals(productDTO.getDescription(), product.getDescription());
		assertEquals(productDTO.getPrice(), product.getPrice());
	    assertNotNull(productDTO.getCategoryDTO());
	    assertNull(productDTO.getCategoryDTO().getProductsDTO());
	}
	
	@Test
	public void testToEntity() {
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("productName");
		productDTO.setDescription("productDescription");
		productDTO.setPrice(10.0);
		
		CategoryDTO categoryDTO = new CategoryDTO();
		List<ProductDTO> productsDTO = new ArrayList<>();
		productsDTO.add(new ProductDTO());
		productsDTO.add(new ProductDTO());
		categoryDTO.setProductsDTO(productsDTO);
		productDTO.setCategoryDTO(categoryDTO);;
		
		Product product = ProductMapper.INSTANCE.toEntity(productDTO);
		
		assertNotNull(product);
		assertEquals(product.getId(), productDTO.getId());
		assertEquals(product.getName(), productDTO.getName());
		assertEquals(product.getDescription(), productDTO.getDescription());
		assertEquals(product.getPrice(), productDTO.getPrice());
		assertNotNull(product.getCategory());
		assertNull(product.getCategory().getProducts());
		
	}

}
