package com.example.productinventorysystem.controller;

import com.example.productinventorysystem.dto.ProductRequestDTO;
import com.example.productinventorysystem.dto.ProductResponseDTO;
import com.example.productinventorysystem.model.User;
import com.example.productinventorysystem.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** GET ALL PRODUCTS (public) **/
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /** GET MY PRODUCTS (authenticated user only) **/
    @GetMapping("/my")
    public ResponseEntity<List<ProductResponseDTO>> getMyProducts(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.getMyProducts(user));
    }

    /** CREATE PRODUCT (authenticated user only) **/
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO dto,
            @AuthenticationPrincipal User user) {
        ProductResponseDTO response = productService.createProduct(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /** UPDATE PRODUCT — only owner can update **/
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.updateProduct(id, dto, user));
    }

    /** DELETE PRODUCT — only owner can delete **/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        productService.deleteProduct(id, user);
        return ResponseEntity.ok("Product deleted successfully");
    }
}


