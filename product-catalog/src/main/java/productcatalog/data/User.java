package productcatalog.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 100)
	private String password;
	
	@NotBlank
	@Size(min = 2, max = 50)
	private String firstname;
	
	@NotBlank
	@Size(min = 2, max = 50)
	private String lastname;
	
	@NotBlank
	@Email
	@Size(max = 100)
	private String email;
	
	@NotBlank
	private String role;
}
