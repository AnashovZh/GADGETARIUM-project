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

    //    Бардык продуктыларды алып жатканда Категория жана прайс аркылуу
    //    фильтрация болуш керек жана прайс боюнча сортировка болуш керек
    @PermitAll
    @GetMapping("/getAllByCategoryAndPrice")
    List<ProductResponse> getAllByCategoryAndPrice(@RequestParam Category category,
                                                   @RequestParam String ascOrDesc) {
        return productService.findAllByCategoryAndPrice(ascOrDesc, category);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    SimpleResponse save(@RequestBody ProductRequest productRequest,
                        @RequestParam(required = false) String brandName) {
        return productService.save(productRequest,brandName);
    }


    @PermitAll
    @GetMapping("/{id}")
    ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    /**
     * Бир продуктыны алып жатканда комментарийлери кошо чыгыш керек
     */
    @PermitAll
    @GetMapping("/getByIdWithComment/{id}")
    ProductResponse getByIdWithComment(@PathVariable Long id) {
        return productService.getByIdWithComment(id);
    }

    /**
     * Бир продукты алып жатанда канча лайк бар экенин чыгарыш керек
     */
    @PermitAll
    @GetMapping("/countLike/{id}")
    ProductResponse getProductWithCountLike(@PathVariable Long id) {
        return productService.getProductWithCountLike(id);
    }

    /**
     * Юзер жактырган продуктысын избранныйга кошо алган жана ал
     * жактан алып салган функционалы болсун
     */
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/favorite/{id}")
    SimpleResponse addingOrRemovingFavorites(@RequestParam String favoriteOrNotFavorite,
                                             @PathVariable Long id) {
        return productService.addingOrRemovingFavorites(favoriteOrNotFavorite, id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/favorite1/{id}")
    SimpleResponse addingOrRemovingFavorites1(@PathVariable Long id) {
        return productService.addingOrRemovingFavorites1( id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateByEmailAndPassword")
    SimpleResponse updateByNameAndCategory(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Category category,
                                           Map<String, Object> fields) {
        return productService.updateByNameAndCategory(name, category, fields);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse update(@PathVariable Long id,
                          @RequestBody Map<String, Object> fields) {
        return productService.update(id, fields);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteByName(@RequestParam String name) {
        return productService.deleteByName(name);
    }
}
