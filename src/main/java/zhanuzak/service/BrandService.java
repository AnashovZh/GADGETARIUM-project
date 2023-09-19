package zhanuzak.service;

import zhanuzak.dto.request.BrandRequest;
import zhanuzak.dto.response.BrandResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAll();

    SimpleResponse save(BrandRequest brandRequest);
}
