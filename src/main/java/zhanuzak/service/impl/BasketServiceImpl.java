package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Basket;
import zhanuzak.models.Product;
import zhanuzak.models.User;
import zhanuzak.repository.BasketRepository;
import zhanuzak.repository.ProductRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.BasketService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<BasketResponse> getAllBasketsByUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));

        List<BasketResponse> allBaskets = basketRepository.findAllBaskets();
//        basketRepository.findBasketById()
        return null;
    }

    @Override
    public SimpleResponse save(Long productId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id :" + productId + " not found!!!"));
        Basket basket = new Basket();
        basket.setUser(user);
        basket.setProducts(List.of(product));
        Basket save = basketRepository.save(basket);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Basket with id:" + save.getId() + " successfully saved ☺")
                .build();
    }


}
