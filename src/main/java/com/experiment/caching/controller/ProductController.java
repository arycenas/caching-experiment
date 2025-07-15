package com.experiment.caching.controller;

import com.experiment.caching.dto.request.ProductRequest;
import com.experiment.caching.dto.response.ProductResponse;
import com.experiment.caching.service.ProductGeneratorService;
import com.experiment.caching.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

  private final ProductGeneratorService productGeneratorService;
  private final ProductService productService;

  @PostMapping("generate")
  public ResponseEntity<ProductResponse> generateProducts(@RequestParam int count) {
    return new ResponseEntity<>(productGeneratorService.generateProducts(count), HttpStatus.OK);
  }

  @PostMapping("search")
  public ResponseEntity<ProductResponse> findProducts(@RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.getProductByKeyword(request), HttpStatus.OK);
  }

  @PostMapping("search/cached")
  public ResponseEntity<ProductResponse> findProductsCached(@RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.getProductByKeywordCached(request), HttpStatus.OK);
  }
}
