package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.response.FavoriteResponse;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteApi {
    private final FavoriteService favoriteService;

    @PermitAll
    @GetMapping()
    List<FavoriteResponse> getAll() {
        return favoriteService.getAll();
    }

    /**
     * Юзер жактырган продуктысын избранныйга кошо алган  функционалы болсун
     */
    @PermitAll
    @PostMapping("/save/{productId}")
    SimpleResponse save(@PathVariable Long productId) {
        return favoriteService.save(productId);
    }

    /**
     * Бир юзердин избранныйдагы продуктыларын алып чыккан функционалы болсун
     */
    @PermitAll
    @GetMapping("/getFavoriteProductsByUser")
    List<ProductResponse> getFavoriteProductsByUser() {
        return favoriteService.getFavoriteProductsByUser();
    }

    /**
     * Юзер жактырган продуктысын избранный жактан алып салган функционалы болсун
     */

    @PermitAll
    @PostMapping("/delete/{productId}")
    SimpleResponse delete(@PathVariable Long productId) {
        return favoriteService.delete(productId);
    }
}
