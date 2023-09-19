package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.FavoriteProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.BasketService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")

public class BasketApi {
    private final BasketService basketService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    List<BasketResponse> getAllBasketsByUser() {
        return basketService.getAllBasketsByUser();
    }

    @PermitAll
    @PostMapping("/save/{productId}")
    SimpleResponse saveBasket(@PathVariable Long productId) {
        return basketService.save(productId);
    }

    @PermitAll
    @PostMapping("/delete/{id}")
    SimpleResponse deleteBasket(@PathVariable Long id) {
        return basketService.deleteBasketById(id);
    }

    @PermitAll
    @PostMapping("/deleteAll")
    SimpleResponse deleteAll() {
        return basketService.deleteAll();
    }

    /**
     * Бир юзердин корзинасындагы бардык товарларды алып чыгып жана ошол
     * товарлардын санын жана суммасын чыгара турган метод болсун
     */
    @PermitAll
    @GetMapping("/favoriteProductsAndSumByUser")
    FavoriteProductResponse favoriteProductsAndSumByUser() {
        return basketService.favoriteProductsAndSumByUser();
    }

}
