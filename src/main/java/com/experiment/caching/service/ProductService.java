package com.experiment.caching.service;

import com.experiment.caching.dto.request.ProductRequest;
import com.experiment.caching.dto.response.ProductResponse;

public interface ProductService {

  ProductResponse getProductByKeyword(ProductRequest request);
}
