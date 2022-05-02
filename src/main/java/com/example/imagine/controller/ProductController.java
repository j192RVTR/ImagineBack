package com.example.imagine.controller;

import com.example.imagine.entity.Product;
import com.example.imagine.exception.ResourceNotFoundException;
import com.example.imagine.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public List<Product> getProductList(@RequestParam String consumerKey) {
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable(value = "productId") String productId) {
        return productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + " not found"));
    }

    @PostMapping("/products")
    public String createProduct(@RequestBody Product product) {
        productService.save(product);
        return "Product added";
    }

    @PutMapping("/products/{productId}")
    public String updateProduct(@PathVariable(value = "productId") String productId, @RequestBody Product product) {
        return productService.findById(productId).map(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            productService.save(p);
            return "Product updated";
        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + " not found"));
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") String productId) {
        return productService.findById(productId).map(p -> {
            productService.deleteById(productId);
            return "Product deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + " not found"));
    }
}
