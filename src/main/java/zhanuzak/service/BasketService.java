package zhanuzak.service;

import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.FavoriteProductResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface BasketService {


    SimpleResponse save(Long productId);

    List<BasketResponse> getAllBasketsByUser();

    SimpleResponse deleteBasketById(Long id);

    SimpleResponse deleteAll();

    FavoriteProductResponse  favoriteProductsAndSumByUser();

}
