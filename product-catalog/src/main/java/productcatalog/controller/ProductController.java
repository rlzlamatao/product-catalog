package productcatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import productcatalog.data.Product;
import productcatalog.dto.ProductDTO;
import productcatalog.service.interfaces.CategoryService;
import productcatalog.service.interfaces.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/products")
	public String getProducts(Model model) {
		 List<ProductDTO> products = productService.getAllProducts();
	        model.addAttribute("products", products);
	        return "products";
	}
	
	@GetMapping("/products/create")
	public String showCreateProductForm(Model model) {
		
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAllCategories());
		return "create-product";
	}

}
