package productcatalog.service.interfaces;

import java.util.List;

import productcatalog.dto.ProductDTO;

public interface ProductService {
	
	ProductDTO saveProduct(ProductDTO productDTO);
	ProductDTO getProduct(Long id);
	List<ProductDTO> getAllProducts();
	ProductDTO updateProduct(Long id, ProductDTO newProductDTO);
	void deleteProduct(Long id);
	
}
