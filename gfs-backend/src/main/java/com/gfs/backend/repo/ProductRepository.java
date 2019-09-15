package com.gfs.backend.repo;

import com.gfs.backend.persistence.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByBarCode(String barCode);
}
