package com.experiment.caching.repository;

import com.experiment.caching.model.Product;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

  List<Product> findByNameContainingIgnoreCase(String name);
}
