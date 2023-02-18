package productcatalog.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import productcatalog.data.Category;
import productcatalog.dto.CategoryDTO;

@Named("CategoryMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
	
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	@Named("toDTO")
	@Mappings({
	    @Mapping(target = "id", source = "category.id"),
	    @Mapping(target = "name", source = "category.name"),
	    @Mapping(target = "productsDTO", source = "category.products")
	})
    CategoryDTO toDTO(Category category);
	
	
	@Named("toDTOWithoutProductsDTO")
	@Mappings({
		@Mapping(target = "id", source = "category.id"),
	    @Mapping(target = "name", source = "category.name"),
        @Mapping(target = "productsDTO", ignore = true)
	})
	CategoryDTO toDTOWithoutProductsDTO(Category category);
	
	
	@Named("toEntity")
	@Mappings({
		@Mapping(target = "id", source = "categoryDTO.id"),
		@Mapping(target = "name", source = "categoryDTO.name"),
		@Mapping(target = "products", source ="categoryDTO.productsDTO")
	})
	Category toEntity(CategoryDTO categoryDTO);
	
	
	@Named("toEntityWithoutProducts")
	@Mappings({
		@Mapping(target = "id", source = "categoryDTO.id"),
		@Mapping(target = "name", source = "categoryDTO.name"),
		@Mapping(target = "products", ignore = true)
	})
	Category toEntityWithoutProducts(CategoryDTO categoryDTO);
}
