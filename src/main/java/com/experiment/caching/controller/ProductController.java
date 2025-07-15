package com.experiment.caching.controller;

import com.experiment.caching.service.ProductGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

  private final ProductGeneratorService productGeneratorService;

  @PostMapping("generate")
  public ResponseEntity<String> generateProducts(@RequestParam int count) {
    return new ResponseEntity<>(productGeneratorService.generateProducts(count), HttpStatus.OK);
  }
}
