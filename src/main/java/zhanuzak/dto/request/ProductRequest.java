package zhanuzak.dto.request;

import lombok.Builder;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;

import java.math.BigDecimal;
import java.util.List;

//    private String name;
//    private BigDecimal price;
//    private List<String>images;
//    private String characteristic;
//    private boolean isFavorite;
//    private Country madeIn;
//    private Category category;
@Builder
public record ProductRequest(String name, BigDecimal price,
                             List<String> images, String characteristic,
                             boolean isFavorite,
                             Country madeIn, Category category) {
}
