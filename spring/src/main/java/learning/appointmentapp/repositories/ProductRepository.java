package learning.appointmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}