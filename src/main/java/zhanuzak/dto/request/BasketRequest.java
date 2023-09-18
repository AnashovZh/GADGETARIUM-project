package zhanuzak.dto.request;

import lombok.Builder;
import zhanuzak.models.Product;
import zhanuzak.models.User;

import java.util.List;
@Builder
public record BasketRequest(Long id, User user, List<Product>products) {

}
