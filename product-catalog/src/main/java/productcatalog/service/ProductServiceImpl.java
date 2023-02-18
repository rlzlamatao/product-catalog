package productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalog.data.Product;
import productcatalog.dto.ProductDTO;
import productcatalog.mapper.ProductMapper;
import productcatalog.repository.ProductRepository;
import productcatalog.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {
		Product product = ProductMapper.INSTANCE.toEntity(productDTO);
		Product savedProduct = productRepository.save(product);
		ProductDTO savedProductDTO = ProductMapper.INSTANCE.toDTO(savedProduct);
		return savedProductDTO;
	}

	@Override
	public ProductDTO getProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow();
		ProductDTO productDTO = ProductMapper.INSTANCE.toDTO(product);
		return productDTO;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productsDTOs = products.stream().map(ProductMapper.INSTANCE::toDTO)
				.collect(Collectors.toList());
		return productsDTOs;
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO newProductDTO) {
		Product product = productRepository.findById(id).map(p -> {
			p.setName(newProductDTO.getName());
			p.setDescription(newProductDTO.getDescription());
			p.setPrice(newProductDTO.getPrice());
			return productRepository.save(p);
		}).orElse(null);
		ProductDTO productDTO = ProductMapper.INSTANCE.toDTO(product);
		return productDTO;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}
