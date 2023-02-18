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
import productcatalog.dto.ProductDTO;
import productcatalog.service.interfaces.ProductService;

@RestController
@RequestMapping("/product")
public class ProductAPI {

	private final ProductService productService;

	@Autowired
	public ProductAPI(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.CREATED);
	} 

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
		return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
