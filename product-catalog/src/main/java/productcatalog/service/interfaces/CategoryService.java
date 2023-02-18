package productcatalog.service.interfaces;

import java.util.List;

import productcatalog.dto.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO saveCategory(CategoryDTO categoryDTO);
	CategoryDTO getCategory(Long id);
	List<CategoryDTO> getAllCategories();
	CategoryDTO updateCategory(Long id, CategoryDTO newCategoryDTO);
	void deleteCategory(Long id);

}
