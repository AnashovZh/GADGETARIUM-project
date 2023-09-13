package zhanuzak.service;

import zhanuzak.dto.request.ProductRequest;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.Category;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductResponse> findAll();

    SimpleResponse save(ProductRequest productRequest);

    ProductResponse getById(Long id);


    SimpleResponse updateByNameAndCategory(String name, Category category, Map<String, Object> fields);

    SimpleResponse update(Long id, Map<String, Object> fields);

    SimpleResponse deleteByName(String name);
}
