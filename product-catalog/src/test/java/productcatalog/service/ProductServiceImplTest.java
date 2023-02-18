package productcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import productcatalog.mapper.ProductMapper;
import productcatalog.repository.ProductRepository;
import productcatalog.service.interfaces.ProductService;

@SpringBootTest
public class ProductServiceImplTest {
	
	@MockBean
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	
	@Test
	public void testSaveProduct() {
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Product Name Test");
		productDTO.setDescription("Product Description Test");
		productDTO.setPrice(10);
		productDTO.setCategoryDTO(new CategoryDTO());
		
		Product product = ProductMapper.INSTANCE.toEntity(productDTO);
		Product savedProduct = product;
		
		when(productRepository.save(product)).thenReturn(savedProduct);
		
		ProductDTO savedProductDTO = productService.saveProduct(productDTO);
		
		verify(productRepository, times(1)).save(product);
		
		assertEquals(productDTO.getId(), savedProductDTO.getId());
		assertEquals(productDTO.getName(), savedProductDTO.getName());
		assertEquals(productDTO.getDescription(), savedProductDTO.getDescription());
		assertEquals(productDTO.getPrice(), savedProductDTO.getPrice());
		assertNotNull(savedProductDTO.getCategoryDTO());
	}
	
	@Test
	public void testGetProduct() {
		
		Long id = 1L;
		
		Product product = new Product();
		product.setId(id);
		product.setName("Product Name Test");
		product.setDescription("Product Description Test");
		product.setPrice(10);
		product.setCategory(new Category());
		
		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		
		ProductDTO productDTO = productService.getProduct(id);
		
		verify(productRepository, times(1)).findById(id);
		
		assertEquals(productDTO.getId(), product.getId());
		assertEquals(productDTO.getName(), product.getName());
		assertEquals(productDTO.getDescription(), product.getDescription());
		assertEquals(productDTO.getPrice(), product.getPrice());
		assertNotNull(productDTO.getCategoryDTO());
	}
	
	@Test
	public void testGetAllProducts() {
		Product product1 = new Product();
		product1.setId(1l);
		product1.setName("Product Name Test");
		product1.setDescription("Product Description Test");
		product1.setPrice(10);
		product1.setCategory(new Category());
		Product product2 = new Product();
		product2.setId(1l);
		product2.setName("Product Name Test");
		product2.setDescription("Product Description Test");
		product2.setPrice(10);
		product2.setCategory(new Category());
		
		List<Product> products = Arrays.asList(product1, product2);
		
		when(productRepository.findAll()).thenReturn(products);
		
		List<ProductDTO> productsDTO = productService.getAllProducts();
		
		verify(productRepository, times(1)).findAll();
		
		assertEquals(productsDTO.size(), products.size());
		assertEquals(productsDTO.get(0).getId(), products.get(0).getId());
		assertEquals(productsDTO.get(0).getName(), products.get(0).getName());
		assertEquals(productsDTO.get(0).getDescription(), products.get(0).getDescription());
		assertEquals(productsDTO.get(0).getPrice(), products.get(0).getPrice());
		assertNotNull(productsDTO.get(0).getCategoryDTO());
		assertEquals(productsDTO.get(1).getId(), products.get(1).getId());
		assertEquals(productsDTO.get(1).getName(), products.get(1).getName());
		assertEquals(productsDTO.get(1).getDescription(), products.get(1).getDescription());
		assertEquals(productsDTO.get(1).getPrice(), products.get(1).getPrice());
		assertNotNull(productsDTO.get(1).getCategoryDTO());
	}
	
	@Test
	public void testUpdateProduct() {
		
		Long id = 1L;
		Product product = new Product();
		product.setId(id);
		product.setName("Product Name Test");
		product.setDescription("Product Description Test");
		product.setPrice(10);
		product.setCategory(new Category());
		
		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		when(productRepository.save(product)).thenReturn(product);
		
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setId(id);
		newProductDTO.setName("Product Name Test updated");
		newProductDTO.setDescription("Product Description Test updated");
		newProductDTO.setPrice(15);
		newProductDTO.setCategoryDTO(new CategoryDTO());
		
		ProductDTO updatedProdctDTO = productService.updateProduct(id, newProductDTO);
		
		verify(productRepository, times(1)).save(product);
		
		assertEquals(updatedProdctDTO.getId(), newProductDTO.getId());
		assertEquals(updatedProdctDTO.getName(), newProductDTO.getName());
		assertEquals(updatedProdctDTO.getDescription(), newProductDTO.getDescription());
		assertEquals(updatedProdctDTO.getPrice(), newProductDTO.getPrice());
		assertNotNull(updatedProdctDTO.getCategoryDTO());
	}
	
	@Test
	public void testDeleteProduct() {
		
		Long id = 1L;
		
		productService.deleteProduct(id);
		
		verify(productRepository, times(1)).deleteById(id);
	}
}
