package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.BasketService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baskets")

public class BasketApi {
    private final BasketService basketService;

    @PermitAll
    @GetMapping()
    List<BasketResponse> getAllBasketsByUser() {
        return basketService.getAllBasketsByUser();
    }

    @PermitAll
    @PostMapping("/save/{productId}")
    SimpleResponse saveBasket(
                              @PathVariable Long productId) {
        return basketService.save( productId);
    }


}
