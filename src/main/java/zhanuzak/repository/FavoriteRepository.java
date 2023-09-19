package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.FavoriteResponse;
import zhanuzak.dto.response.ProductResponse;
import zhanuzak.models.Favorite;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("select new zhanuzak.dto.response.FavoriteResponse(f.id,f.user.firstName,f.product.name)from Favorite f")
    List<FavoriteResponse> findAllFavorites();

//    @Query("select f.id from Favorite f join User u on f.user.id=?1 join Product  p on f.product.id=?2 ")
//    Long existsUserAndProduct(Long userId, Long productId);
//    private Long id;
//    private String name;
//    private BigDecimal price;
//    private List<String> images;
//    private String characteristic;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
    @Query(nativeQuery = true,value = "SELECT COUNT(*) FROM favorites\n" +
            "WHERE user_id = ?1 AND product_id = ?2")
    int existsUserAndProduct(Long userId,Long productId);
    @Query("select new zhanuzak.dto.response.ProductResponse(p.id,p.name,p.price,p.characteristic,p.isFavorite,p.madeIn,p.category) from Favorite f join Product p on f.product.id=p.id join User u on f.user.id=u.id where u.id=?1 ")
   List<ProductResponse>getFavoriteProductByUserEmail(Long id);

}