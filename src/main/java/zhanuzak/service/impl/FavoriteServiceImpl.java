package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.response.FavoriteResponse;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Favorite;
import zhanuzak.models.Product;
import zhanuzak.models.User;
import zhanuzak.repository.CommentRepository;
import zhanuzak.repository.FavoriteRepository;
import zhanuzak.repository.ProductRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.FavoriteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<FavoriteResponse> getAll() {
        return favoriteRepository.findAllFavorites();
    }

    @Override
    public List<ProductResponse> getFavoriteProductsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        List<ProductResponse> favoriteProductByUserEmail = favoriteRepository.getFavoriteProductByUserEmail(user.getId());
        return favoriteProductByUserEmail;
    }

    @Override
    public SimpleResponse save(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id:" + " not found !!!"));
        Favorite favorite = new Favorite();
        favorite.setProduct(product);
        favorite.setUser(user);
        Favorite save = favoriteRepository.save(favorite);
        product.setFavorite(true);
        productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved favorite with id:" + save.getId())
                .build();
    }

    @Override
    public SimpleResponse delete(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id:" + " not found !!!"));
        int i = favoriteRepository.existsUserAndProduct(user.getId(), product.getId());
        if (i > 0) {


        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted favorite with id:")
                .build();
    }
}
