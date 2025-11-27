package com.example.productinventorysystem.repository;

import com.example.productinventorysystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // List products that belong to a specific user
    List<Product> findByUserId(Long userId);
}

