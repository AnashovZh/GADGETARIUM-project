package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.FavoriteProductResponse;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Basket;
import zhanuzak.models.Product;
import zhanuzak.models.User;
import zhanuzak.repository.BasketRepository;
import zhanuzak.repository.FavoriteRepository;
import zhanuzak.repository.ProductRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.BasketService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<BasketResponse> getAllBasketsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Long id = user.getBasket().getId();
        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setId(id);
        String fullName= user.getFirstName()+" "+user.getLastName();
        basketResponse.setFullName(fullName);
        List<String> productsName = new ArrayList<>();
        List<Product> products = user.getBasket().getProducts();
        for (Product p : products) {
            productsName.add(p.getName());
        }
        basketResponse.setProductNames(productsName);
        return List.of(basketResponse);
    }

    @Override
    public FavoriteProductResponse favoriteProductsAndSumByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Basket basket = user.getBasket();
        List<Product> products = basket.getProducts();
        FavoriteProductResponse favoriteProductResponse = new FavoriteProductResponse();
        List<ProductResponse> productResponses = new ArrayList<>();
        favoriteProductResponse.setProductResponse(productResponses);
        for (Product p : products) {
            productResponses.add(new ProductResponse().build(p));
        }
        favoriteProductResponse.setCountProducts(products.size());
        favoriteProductResponse.setSumProducts(basketRepository.sumProductsByPrice(user.getId()));
        return favoriteProductResponse;
    }

    @Override
    public SimpleResponse deleteBasketById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Basket basket = user.getBasket();
        if (basket != null) {
            List<Product> products = basket.getProducts();
            for (Product p : products) {
                if (p.getId().equals(id)) {
                    basketRepository.delete(basket);
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message("Product with id:" + basket.getId() + " successfully deleted !!!")
                            .build();
                } else {
                    return SimpleResponse.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .message("Product with id:" + p.getId() + " not found !!!")
                            .build();
                }
            }
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Basket  not found !!!")
                    .build();
        }
        return null;
    }

    @Override
    public SimpleResponse deleteAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        List<Product> products1 = user.getBasket().getProducts();
        List<Long> ids = new ArrayList<>();
        List<Basket> baskets = new ArrayList<>();
        for (Product p : products1) {
            ids.add(p.getId());
        }
        for (Long id : ids) {
            baskets.add(basketRepository.findByProductId(id));

        }
        for (Basket b : baskets) {
            if (b != null) {
                basketRepository.delete(b);
            } else {
                new NotFoundException(" not found");
            }
        }
        System.err.println("Baskets:" + baskets);
        System.err.println("Id:" + ids);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted all baskets")
                .build();
    }

    @Override
    public SimpleResponse save(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
