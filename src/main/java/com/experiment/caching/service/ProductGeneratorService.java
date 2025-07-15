package com.experiment.caching.service;

import com.experiment.caching.dto.response.ProductResponse;

public interface ProductGeneratorService {

  ProductResponse generateProducts(int count);
}
