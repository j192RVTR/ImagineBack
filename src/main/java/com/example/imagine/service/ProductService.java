package com.example.imagine.service;

import com.example.imagine.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);

    void deleteById(String id);
    Optional<Product> findById(String id);
    List<Product> findAll();
}
