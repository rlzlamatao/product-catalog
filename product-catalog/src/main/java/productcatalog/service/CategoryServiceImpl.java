package productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalog.data.Category;
import productcatalog.dto.CategoryDTO;
import productcatalog.mapper.CategoryMapper;
import productcatalog.mapper.ProductMapper;
import productcatalog.repository.CategoryRepository;
import productcatalog.service.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
		Category category = CategoryMapper.INSTANCE.toEntity(categoryDTO);
		Category savedCategory = categoryRepository.save(category);
		CategoryDTO savedCategoryDTO = CategoryMapper.INSTANCE.toDTO(savedCategory);
		return savedCategoryDTO;
	}

	@Override
	public CategoryDTO getCategory(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow();
		CategoryDTO categoryDTO = CategoryMapper.INSTANCE.toDTO(category);
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoriesDTO = categories.stream().map(CategoryMapper.INSTANCE::toDTO)
				.collect(Collectors.toList());
		return categoriesDTO;
	}

	@Override
	public CategoryDTO updateCategory(Long id, CategoryDTO newCategoryDTO) {
		Category updatedCategory = categoryRepository.findById(id).map(c -> {
			c.setName(newCategoryDTO.getName());
			c.setProducts(newCategoryDTO.getProductsDTO().stream().map(ProductMapper.INSTANCE::toEntity)
					.collect(Collectors.toList()));
			return categoryRepository.save(c);
		}).orElse(null);
		CategoryDTO updatedCategoryDTO = CategoryMapper.INSTANCE.toDTO(updatedCategory);
		return updatedCategoryDTO;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

}
