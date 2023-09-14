package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.enums.Category;
import zhanuzak.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new zhanuzak.dto.response.ProductResponse (p.id,p.name,p.price,p.images,p.characteristic,p.isFavorite,p.madeIn,p.category) from Product p")
    List<ProductResponse> findAllProducts();

//    @Query("SELECT NEW zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.images,p.characteristic,p.isFavorite,p.madeIn,p.category) FROM Product p WHERE p.category = :category ORDER BY p.price asc ")
//    List<ProductResponse> findAllByCategoryAndPriceAsc(Category category);
@Query("SELECT NEW zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.images,p.characteristic,p.isFavorite,p.madeIn,p.category) FROM Product p WHERE p.category = :category  ORDER BY p.price asc ")
List<ProductResponse> findAllByCategoryAndPriceAsc(@Param("category") Category category);
//
//    @Query(value = "SELECT p.id, p.name, p.price, p.characteristic, p.is_favorite, p.made_in, p.category ,i.images" +
//            "from products p join product_images i on p.id=i.product_id " +
//            "WHERE p.category = :category " +
//            "ORDER BY p.price ASC", nativeQuery = true)
//    List<ProductResponse> findAllByCategoryAndPriceAsc(String category);

    @Query("SELECT NEW zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.images,p.characteristic,p.isFavorite,p.madeIn,p.category) FROM Product p WHERE p.category = :category ORDER BY p.price desc ")
    List<ProductResponse> findAllByCategoryAndPriceDesc(@Param("category") Category category);

    Optional<Product> findByNameAndCategory(String name, Category category);

    Optional<Product> findByName(String name);
}
