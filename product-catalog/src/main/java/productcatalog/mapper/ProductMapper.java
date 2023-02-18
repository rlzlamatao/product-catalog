package productcatalog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import productcatalog.data.Product;
import productcatalog.dto.ProductDTO;

@Named("ProductMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class})
public interface ProductMapper {
	
	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Named("toDTO")
	@Mappings({
	    @Mapping(target = "name", source = "product.name"),
	    @Mapping(target = "description", source = "product.description"),
	    @Mapping(target = "price", source = "product.price"),
	    @Mapping(target = "categoryDTO", source = "product.category", 
	    			qualifiedByName = {"CategoryMapper", "toDTOWithoutProductsDTO"})
	})
    ProductDTO toDTO(Product product);
	
	
	@Named("toEntity")
	@Mappings({
	    @Mapping(target = "name", source = "productDTO.name"),
	    @Mapping(target = "description", source = "productDTO.description"),
	    @Mapping(target = "price", source = "productDTO.price"),
	    @Mapping(target = "category", source = "productDTO.categoryDTO", 
	    			qualifiedByName = {"CategoryMapper", "toEntityWithoutProducts"})
	})
	Product toEntity(ProductDTO productDTO);

}
