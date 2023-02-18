package productcatalog.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Products", uniqueConstraints={
    @UniqueConstraint(columnNames={"name", "description"})
    
})
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1) 
	private Long id;
	
	@Column(unique = true)
	@NotBlank
	@Size(min=2, max=100)
	private String name;
	
	@Column(unique = true)
	@NotBlank
	@Size(min=10, max=1000)
	private String description;
	
	@Min(value=0)
	private double price;
	
	@ManyToOne
	private Category category;
	
}
