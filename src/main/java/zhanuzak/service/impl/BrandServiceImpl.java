package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import zhanuzak.dto.request.BrandRequest;
import zhanuzak.dto.response.BrandResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.models.Brand;
import zhanuzak.repository.BrandRepository;
import zhanuzak.service.BrandService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponse> getAll() {
        return brandRepository.getAll();
    }

    @Override
    public SimpleResponse save(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.brandImage());
        Brand save = brandRepository.save(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Successfully saved brand with name:" + save.getBrandName())
                .build();
    }
}
