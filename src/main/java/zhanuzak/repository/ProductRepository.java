package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.enums.Category;
import zhanuzak.models.Product;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new zhanuzak.dto.response.ProductResponse (p.id,p.name,p.price,p.characteristic,p.isFavorite,p.madeIn,p.category) from Product p")
    List<ProductResponse> findAllProducts();
    @Query("SELECT NEW zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.characteristic,p.isFavorite,p.madeIn,p.category) FROM Product p WHERE p.category = ?1 ORDER BY p.price asc ")
    List<ProductResponse> findAllByCategoryAndPriceAsc( Category category);
    @Query("SELECT NEW zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.characteristic,p.isFavorite,p.madeIn,p.category) FROM Product p WHERE p.category = ?1 ORDER BY p.price desc ")
    List<ProductResponse> findAllByCategoryAndPriceDesc(Category category);
    @Query("select p.id,p.name,p.price,p.characteristic,p.isFavorite,p.madeIn,p.category from Product  p where p.name=?1 or p.category=?2")
    Optional<Product> getProduct(String name, Category category);
    Optional<Product>findProductByNameOrCategory(String name,Category category);

    Optional<Product> findByName(String name);
    @Query("select p.images from Product p where p.id=?1")
    List<String> images(Long productId);
}
