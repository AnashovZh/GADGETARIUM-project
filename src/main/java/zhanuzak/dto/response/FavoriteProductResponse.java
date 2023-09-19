package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FavoriteProductResponse {
    /**Бир юзердин корзинасындагы бардык товарларды алып чыгып жана
     * ошол товарлардын санын жана суммасын чыгара турган метод болсун*/
    private List< ProductResponse> productResponse;
    private int countProducts;
    private int sumProducts;

    public FavoriteProductResponse(List<ProductResponse> productResponse, int countProducts, int sumProducts) {
        this.productResponse = productResponse;
        this.countProducts = countProducts;
        this.sumProducts = sumProducts;
    }
}
