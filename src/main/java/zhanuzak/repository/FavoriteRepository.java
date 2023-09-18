package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.FavoriteResponse;
import zhanuzak.models.Favorite;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("select new zhanuzak.dto.response.FavoriteResponse(f.id,f.user.firstName,f.product.name)from Favorite f")
    List< FavoriteResponse> findAllFavorites();
@Query("select f.id from Favorite f join User u on f.user.id=?1 join Product  p on f.product.id=?2 ")
   Long existsUserAndProduct(Long userId, Long productId);
}