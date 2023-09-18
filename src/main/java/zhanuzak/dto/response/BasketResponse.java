package zhanuzak.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter


public class BasketResponse {
    private Long id;
    private UserResponse user;
    private List<ProductResponse>products;

    public BasketResponse(Long id) {
        this.id = id;
    }

    public BasketResponse() {
    }

}
