package zhanuzak.service;

import zhanuzak.dto.request.ProductRequest;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.Category;

import java.util.List;
import java.util.Map;

public interface ProductService {


    SimpleResponse save(ProductRequest productRequest, String brandName);

    ProductResponse getById(Long id);


    SimpleResponse updateByNameAndCategory(String name, Category category, Map<String, Object> fields);

    SimpleResponse update(Long id, Map<String, Object> fields);

    SimpleResponse deleteByName(String name);

    List<ProductResponse> findAllByCategoryAndPrice(String ascOrDesc, Category category);

    ProductResponse getByIdWithComment(Long id);

    ProductResponse getProductWithCountLike(Long id);

    SimpleResponse addingOrRemovingFavorites(String favoriteOrNotFavorite, Long id);

    SimpleResponse addingOrRemovingFavorites1(Long id);
}
