package com.experiment.caching.service.implementation;

import com.experiment.caching.dto.request.ProductRequest;
import com.experiment.caching.dto.response.ProductResponse;
import com.experiment.caching.model.Product;
import com.experiment.caching.repository.ProductRepository;
import com.experiment.caching.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

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
    productResponse.setMessage("Products fetched successfully");
    productResponse.setTime("%d ms".formatted(endTime - startTime));
    productResponse.setProducts(products);

    return productResponse;
  }
}
