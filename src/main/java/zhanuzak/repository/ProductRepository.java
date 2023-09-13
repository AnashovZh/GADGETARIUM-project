package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.enums.Category;
import zhanuzak.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select new zhanuzak.dto.response.ProductResponse (p.id,p.name,p.price,p.images,p.characteristic,p.isFavorite,p.madeIn,p.category) from Product p")
    List<ProductResponse> findAllProducts();
    Optional<Product> findByNameAndAndCategory(String name, Category category);
//    void deleteProductByName(String name);
    @Query("delete  from Product p where p.name=:name")
    void deleteProductByName(String name);

    Optional<Product> findByNameAndCategory(String name, Category category);

    Optional<Product> findByName(String name);
}
