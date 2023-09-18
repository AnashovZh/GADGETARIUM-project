package zhanuzak.service;

import zhanuzak.dto.response.FavoriteResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface FavoriteService {
    List<FavoriteResponse> getAll();

    SimpleResponse save(Long productId);
}
