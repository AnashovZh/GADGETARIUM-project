package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.request.BasketRequest;
import zhanuzak.dto.response.BasketResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.models.Basket;
import zhanuzak.models.User;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    @Query("select new zhanuzak.dto.response.BasketResponse(b.id)from Basket b ")
    List<BasketResponse>findAllBaskets();
    @Query("select  new zhanuzak.dto.response.UserResponse(u.firstName,u.lastName,u.email,u.password,u.createdDate,u.role)from User u join Basket b where u.basket.id=?1")
    UserResponse findBasketById(Long basketId);
}
