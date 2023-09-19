package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.BrandRequest;
import zhanuzak.dto.response.BrandResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor

public class BrandApi {
    private final BrandService brandService;

    @PermitAll
    @GetMapping
    List<BrandResponse> getAll() {
        return brandService.getAll();
    }

    @PermitAll
    @PostMapping
    SimpleResponse save(@RequestBody BrandRequest brandRequest) {
        return brandService.save(brandRequest);
    }
}
