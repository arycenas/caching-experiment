package com.experiment.caching.service.implementation;

import com.experiment.caching.dto.request.ProductRequest;
import com.experiment.caching.dto.response.ProductResponse;
import com.experiment.caching.model.Product;
import com.experiment.caching.repository.ProductRepository;
import com.experiment.caching.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public ProductResponse getProductByKeyword(ProductRequest request) {
    long startTime = System.currentTimeMillis();
    List<Product> products = productRepository.findByNameContainingIgnoreCase(request.getKeyword());
    long endTime = System.currentTimeMillis();

    log.info(
        "[ProductService] {} products fetched from database for keyword '{}'",
        products.size(),
        request.getKeyword());

    ProductResponse productResponse = new ProductResponse();
    productResponse.setMessage("Products fetched successfully from database");
    productResponse.setTime("%d ms".formatted(endTime - startTime));
    productResponse.setProducts(products);

    return productResponse;
  }

  @Override
  public ProductResponse getProductByKeywordCached(ProductRequest request) {
    long startTime = System.currentTimeMillis();
    Object cached = redisTemplate.opsForValue().get(request.getKeyword());
    if (cached != null) {
      List<Product> cachedProducts = objectMapper.convertValue(cached, new TypeReference<>() {});
      long endTime = System.currentTimeMillis();

      log.info(
          "[ProductService] {} products fetched from Redis for keyword '{}'",
          cachedProducts.size(),
          request.getKeyword());

      ProductResponse productResponse = new ProductResponse();
      productResponse.setMessage("Products fetched successfully from Redis");
      productResponse.setTime("%d ms".formatted(endTime - startTime));
      productResponse.setProducts(cachedProducts);

      return productResponse;
    }

    List<Product> productsFromDb =
        productRepository.findByNameContainingIgnoreCase(request.getKeyword());
    redisTemplate.opsForValue().set(request.getKeyword(), productsFromDb, Duration.ofMinutes(5));
    long endTime = System.currentTimeMillis();

    log.info(
        "[ProductService] {} products fetched from database and cached for keyword '{}'",
        productsFromDb.size(),
        request.getKeyword());

    ProductResponse productResponse = new ProductResponse();
    productResponse.setMessage("Products fetched successfully from database and cached to Redis");
    productResponse.setTime("%d ms".formatted(endTime - startTime));
    productResponse.setProducts(productsFromDb);

    return productResponse;
  }
}
