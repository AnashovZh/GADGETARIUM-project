package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zhanuzak.dto.request.ProductRequest;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;
import zhanuzak.exceptions.exception.NonUniqueResultException;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Brand;
import zhanuzak.models.Favorite;
import zhanuzak.models.Product;
import zhanuzak.models.User;
import zhanuzak.repository.*;
import zhanuzak.service.ProductService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final BrandRepository brandRepository;

    //    private String name;
//    private BigDecimal price;
//    private List<String>images;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
    @Override
    public List<ProductResponse> findAllByCategoryAndPrice(String ascOrDesc, Category category) {
        if (ascOrDesc.equalsIgnoreCase("asc") || ascOrDesc.equalsIgnoreCase("desc")) {
            if (ascOrDesc.equalsIgnoreCase("asc")) {
                log.info("first method run");
                List<ProductResponse> allByCategoryAndPriceAsc = productRepository.findAllByCategoryAndPriceAsc(category);
                for (ProductResponse p : allByCategoryAndPriceAsc) {
                    p.setImages(productRepository.images(p.getId()));
                }
                return allByCategoryAndPriceAsc;
            } else if (ascOrDesc.equalsIgnoreCase("desc")) {
                log.info("second method run");
                List<ProductResponse> allByCategoryAndPriceDesc = productRepository.findAllByCategoryAndPriceDesc(category);
                for (ProductResponse p : allByCategoryAndPriceDesc) {
                    p.setImages(productRepository.images(p.getId()));
                }
                return allByCategoryAndPriceDesc;
            }
        } else {
            throw new NotFoundException("Input wrong correctly(asc,desc)");
        }
        return null;
    }


    @Override
    public SimpleResponse save(ProductRequest productRequest, String brandName) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setImages(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setFavorite(productRequest.isFavorite());
        product.setMadeIn(productRequest.madeIn());
        product.setCategory(productRequest.category());
        Brand brand = brandRepository.findBrandByBrandName(brandName);
        product.setBrand(brand);
        Product save = productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Product with name:" + save.getName() + " successfully saved ☺ with id:" + save.getId())
                .build();
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id:" + id + " not found !!!"));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setImages(product.getImages());
        productResponse.setCharacteristic(product.getCharacteristic());
        productResponse.setFavorite(product.isFavorite());
        productResponse.setMadeIn(product.getMadeIn());
        productResponse.setCategory(product.getCategory());
        return productResponse;
    }

    @Override
    public ProductResponse getByIdWithComment(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id:" + id + " not found !!!"));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setImages(product.getImages());
        productResponse.setCharacteristic(product.getCharacteristic());
        productResponse.setFavorite(product.isFavorite());
        productResponse.setMadeIn(product.getMadeIn());
        productResponse.setCategory(product.getCategory());
        productResponse.setComments(commentRepository.comments(id));
        productResponse.setCountComment(commentRepository.countComments(id));
        productResponse.setCountCommentsByUser(commentRepository.countCommentsByUser(id));
        productResponse.setFirstNameCommentsByUser(commentRepository.getFirstNameCommentsByUser(id));
        return productResponse;
    }

    @Override
    public ProductResponse getProductWithCountLike(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id:" + id + " not found !!!"));
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setImages(product.getImages());
        productResponse.setCharacteristic(product.getCharacteristic());
        productResponse.setFavorite(product.isFavorite());
        productResponse.setMadeIn(product.getMadeIn());
        productResponse.setCategory(product.getCategory());
        productResponse.setComments(commentRepository.comments(product.getId()));
        int countComments = commentRepository.countComments(product.getId());
        productResponse.setCountComment(countComments);
        productResponse.setCountCommentsByUser(commentRepository.countCommentsByUser(id));
        productResponse.setFirstNameCommentsByUser(commentRepository.getFirstNameCommentsByUser(id));
        productResponse.setCountLike(commentRepository.countLike(id));
        int i = commentRepository.countLike(id);
        log.info("countLike");
        System.out.println(i);
        return productResponse;
    }

    @Override
    public SimpleResponse addingOrRemovingFavorites(String favoriteOrNotFavorite, Long id) {

        if (favoriteOrNotFavorite.equalsIgnoreCase("favorite") ||
                favoriteOrNotFavorite.equalsIgnoreCase("notFavorite")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("User with email:" + email + " not found !!!"));
            Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                    "Product with Id:" + id + " not found !!!"));
            if (favoriteOrNotFavorite.equalsIgnoreCase("favorite")) {
                Favorite favorite = new Favorite();
                favorite.setProduct(product);
                favorite.setUser(user);
                Favorite save = favoriteRepository.save(favorite);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.CREATED)
                        .message("Favorite with id:" + save.getId() + " successfully saved ☺ " + " " +
                                user.getFirstName() + " likes this product name:" + product.getName())

                        .build();
            } else if (favoriteOrNotFavorite.equalsIgnoreCase("notFavorite")) {
                Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
                String email1 = authentication1.getName();
                User user1 = userRepository.getUserByEmail(email1).orElseThrow(() ->
                        new NotFoundException("User with email:" + email1 + " not found !!!"));
                Product product1 = productRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                        "Product with Id:" + id + " not found !!!"));
                List<Favorite> favorites = favoriteRepository.findAll();
                for (Favorite f : favorites) {
                    if (f.getUser().equals(user1) && f.getProduct().equals(product1)) {
                        favoriteRepository.deleteById(f.getId());
                        return SimpleResponse.builder()
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .message("Successfully deleted ☺ with id:" + f.getId())
                                .build();
                    }
                }
            } else {
                throw new NotFoundException("Wrong input else !!!(favoriteOrNotFavorite)");
            }
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Wrong input !!!(favoriteOrNotFavorite)")
                .build();
    }

    @Override
    public SimpleResponse addingOrRemovingFavorites1(Long id) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        if (authentication1 == null || !authentication1.isAuthenticated()) {
            throw new NotFoundException("Authentication required to delete a comment !!!");
        }
        String email1 = authentication1.getName();
        User user1 = userRepository.getUserByEmail(email1).orElseThrow(() ->
                new NotFoundException("User with email:" + email1 + " not found !!!"));
        Product product1 = productRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                "Product with Id:" + id + " not found !!!"));
        List<Favorite> favorites = favoriteRepository.findAll();
        for (Favorite f : favorites) {
            if (f.getUser().equals(user1) && f.getProduct().equals(product1)) {
                favoriteRepository.deleteById(f.getId());
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("Successfully deleted ☺ with id:" + f.getId())
                        .build();
            }
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Successfully deleted ☺ with id:")
                .build();


    }

    @Override
    public SimpleResponse updateByNameAndCategory(String name,
                                                  Category category,
                                                  Map<String, Object> fields) {
        Optional<Product> product = productRepository.findProductByNameOrCategory(name, category);
        if (product.isEmpty()) {
            throw new NotFoundException("Product with name: " + name + " or category: " + category + " not found !!!");
        } else if (product.isPresent()) {
            throw new NonUniqueResultException("There are multiple Products with the same name or category in the database.");
        }
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = null;
                if (value instanceof String && field.getType() == String.class) {
                    fieldValue = value;
                } else if (value instanceof Country && field.getType() == Country.class) {
                    fieldValue = value;
                } else if (field.getType() == Category.class && value instanceof String) {
                    fieldValue = EnumUtils.getEnumIgnoreCase(Category.class, (String) value);
                } else if (field.getType() == Country.class && value instanceof String) {
                    fieldValue = EnumUtils.getEnumIgnoreCase(Country.class, (String) value);
                } else if (field.getType() == boolean.class && value instanceof String) {
                    fieldValue = Boolean.parseBoolean((String) value);
                }
                if (fieldValue != null) {
                    ReflectionUtils.setField(field, product, fieldValue); // Используйте fieldValue, а не value
                }
            }
        });
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Product with name:" + name + " successfully updated")
                .build();
    }


    @Override
    public SimpleResponse update(Long id, Map<String, Object> fields) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id:" + id + " not found !!!"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = null;
                if (value instanceof String && field.getType() == String.class) {
                    fieldValue = value;
                } else if (value instanceof Country && field.getType() == Country.class) {
                    fieldValue = value;
                } else if (field.getType() == Category.class && value instanceof String) {
                    fieldValue = EnumUtils.getEnumIgnoreCase(Category.class, (String) value);
                } else if (field.getType() == Country.class && value instanceof String) {
                    fieldValue = EnumUtils.getEnumIgnoreCase(Country.class, (String) value);
                } else if (field.getType() == boolean.class && value instanceof String) {
                    fieldValue = Boolean.parseBoolean((String) value);
                }
                if (fieldValue != null) {
                    ReflectionUtils.setField(field, product, fieldValue); // Используйте fieldValue, а не value
                }
            }
        });
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Product with id:" + id + " successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() -> new NotFoundException(
                "Product with name:" + name + " not found !!!"));
        productRepository.delete(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Product with name:" + name + " successfully updated")
                .build();
    }
}
