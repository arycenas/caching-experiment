package com.experiment.caching.service.implementation;

import com.experiment.caching.dto.response.ProductResponse;
import com.experiment.caching.model.Product;
import com.experiment.caching.repository.ProductRepository;
import com.experiment.caching.service.ProductGeneratorService;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductGeneratorServiceImpl implements ProductGeneratorService {

  private final ProductRepository productRepository;

  @Override
  public ProductResponse generateProducts(int count) {
    Faker faker = new Faker();
    List<Product> products = new ArrayList<>();

    try {
      for (int i = 0; i < count; i++) {
        Product product =
            Product.builder()
                .name(faker.commerce().productName())
                .description(faker.lorem().sentence())
                .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                .build();

        products.add(product);
      }

      ProductResponse productResponse = new ProductResponse();
      productResponse.setMessage("Success generate %d products".formatted(products.size()));

      productRepository.saveAll(products);

      log.info("[ProductGeneratorService] {} products generated", products.size());

      return productResponse;
    } catch (Exception e) {
      log.error("[ProductGeneratorService] {} products failed", products.size());

      throw new RuntimeException("Failed to generate products: ", e);
    }
  }
}
