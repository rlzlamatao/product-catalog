package productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productcatalog.data.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
