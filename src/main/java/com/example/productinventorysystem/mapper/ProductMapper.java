package com.example.productinventorysystem.mapper;

import com.example.productinventorysystem.dto.ProductRequestDTO;
import com.example.productinventorysystem.dto.ProductResponseDTO;
import com.example.productinventorysystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    public ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setUserId(product.getUser().getId());
        return dto;
    }
}


