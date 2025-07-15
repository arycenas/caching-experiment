package com.experiment.caching.dto.response;

import com.experiment.caching.model.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {

  private String message;
  private String time;
  private List<Product> products = new ArrayList<>();
}
