package com.example.productinventorysystem.service;

import com.example.productinventorysystem.dto.ProductRequestDTO;
import com.example.productinventorysystem.dto.ProductResponseDTO;
import com.example.productinventorysystem.exception.ProductNotFoundException;
import com.example.productinventorysystem.mapper.ProductMapper;
import com.example.productinventorysystem.model.Product;
import com.example.productinventorysystem.model.User;
import com.example.productinventorysystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /** LIST ALL PRODUCTS **/
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    /** LIST PRODUCTS OWNED BY THE AUTHENTICATED USER **/
    public List<ProductResponseDTO> getMyProducts(User user) {
        return productRepository.findByUserId(user.getId())
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    /** CREATE PRODUCT (user owns it) **/
    public ProductResponseDTO createProduct(ProductRequestDTO dto, User user) {
        Product product = productMapper.toEntity(dto);
        product.setUser(user);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    /** UPDATE PRODUCT — only owner can update **/
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto, User user) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id)
                );

        if (!product.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You do not own this product");
        }

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        Product updated = productRepository.save(product);
        return productMapper.toDTO(updated);
    }

    /** DELETE PRODUCT — only owner can delete **/
    public void deleteProduct(Long id, User user) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id)
                );

        if (!product.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You do not own this product");
        }

        productRepository.delete(product);
    }
}

