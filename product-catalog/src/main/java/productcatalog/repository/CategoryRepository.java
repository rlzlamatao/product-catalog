package productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productcatalog.data.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
