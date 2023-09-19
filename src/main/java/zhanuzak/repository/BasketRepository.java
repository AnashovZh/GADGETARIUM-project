package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.models.Basket;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    @Query("select new zhanuzak.dto.response.BasketResponse(b.id)from Basket b ")
    List<BasketResponse>findAllBaskets();
    @Query("select  new zhanuzak.dto.response.UserResponse(u.firstName,u.lastName,u.email,u.password,u.createdDate,u.role)from User u join Basket b where u.basket.id=?1")
    UserResponse findBasketById(Long basketId);
    @Query("SELECT b FROM Basket b JOIN b.products as p WHERE p.id =?1")
    Basket findByProductId(Long productId);
    @Query("select  sum (p.price) from Basket b join b.products as p join User u on b.user.id=u.id where u.id=?1")
    int sumProductsByPrice(Long userId);

}
