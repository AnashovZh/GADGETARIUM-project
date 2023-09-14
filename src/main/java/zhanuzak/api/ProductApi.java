package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.ProductRequest;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.Category;
import zhanuzak.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @PermitAll
    @GetMapping
    List<ProductResponse> getAll() {
        return productService.findAll();
    }

    //    Бардык продуктыларды алып жатканда Категория жана прайс аркылуу
//    фильтрация болуш керек жана прайс боюнча сортировка болуш керек
    @PermitAll
    @GetMapping("/getAllByCategoryAndPrice")
    List<ProductResponse> getAllByCategoryAndPrice(@RequestParam Category category,
                                                   @RequestParam String price) {
        return productService.findAllByCategoryAndPrice(price,category);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.save(productRequest);
    }


    @PermitAll
    @GetMapping("/{id}")
    ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateByEmailAndPassword")
    SimpleResponse updateByEmailAndPassword(@RequestParam String name,
                                            @RequestParam Category category,
                                            Map<String, Object> fields) {
        return productService.updateByNameAndCategory(name, category, fields);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse update(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return productService.update(id, fields);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteByName(@RequestParam String name) {
        return productService.deleteByName(name);
    }
}
