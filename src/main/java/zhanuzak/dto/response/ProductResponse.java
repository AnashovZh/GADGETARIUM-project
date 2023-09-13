package zhanuzak.dto.response;

import lombok.*;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;
import zhanuzak.enums.Role;

import java.math.BigDecimal;
import java.util.List;

//    private Long id;
//    private String name;
//    private BigDecimal price;
//    private List<String> images;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
//    private Role role;
@Builder
@Getter@Setter
@NoArgsConstructor

public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String>images;
    private String characteristic;
    private boolean isFavorite;
    private Country madeIn;
    private Category category;

    public ProductResponse(Long id, String name, BigDecimal price,
                           List<String> images,String characteristic, boolean isFavorite,
                           Country madeIn, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic=characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
    }
}
