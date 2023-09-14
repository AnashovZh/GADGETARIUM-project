package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zhanuzak.dto.request.ProductRequest;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Product;
import zhanuzak.repository.ProductRepository;
import zhanuzak.service.ProductService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAllProducts();
    }

    //    private String name;
//    private BigDecimal price;
//    private List<String>images;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
    @Override
    public SimpleResponse save(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setImages(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setFavorite(productRequest.isFavorite());
        product.setMadeIn(productRequest.madeIn());
        product.setCategory(productRequest.category());
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
    public SimpleResponse updateByNameAndCategory(String name,
                                                  Category category,
                                                  Map<String, Object> fields) {
        Product product = productRepository.findByNameAndCategory(name, category)
                .orElseThrow(() ->
                        new NotFoundException("Product with name:" + name + " not found !!!"));
        log.info("product:" + product.getName());

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);

                Object fieldValue;
                try {
                    fieldValue = ReflectionUtils.getField(field, product);
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }

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
                    ReflectionUtils.setField(field, product, fieldValue);
                    log.info("success");
                }
            }
        });

        productRepository.save(product);

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

    @Override
    public List<ProductResponse> findAllByCategoryAndPrice(String price, Category category) {
        if (price.equalsIgnoreCase("asc") || price.equalsIgnoreCase("desc")) {
            if (price.equalsIgnoreCase("asc")) {
                return productRepository.findAllByCategoryAndPriceAsc(category);
            } else if (price.equalsIgnoreCase("desc")) {
                return productRepository.findAllByCategoryAndPriceDesc(category);
            }
        }
        return null;
    }
}
