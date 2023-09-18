package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteResponse {
    private Long id;
    private String firstName;
    private String productName;


    public FavoriteResponse(Long id, String firstName, String productName) {
        this.id = id;
        this.firstName = firstName;
        this.productName = productName;
    }
}
