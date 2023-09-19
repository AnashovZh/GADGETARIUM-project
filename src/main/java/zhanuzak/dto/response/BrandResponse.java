package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {
    private Long id;
    private String brandName;
    private String brandImage;

    public BrandResponse(Long id, String brandName, String brandImage) {
        this.id = id;
        this.brandName = brandName;
        this.brandImage = brandImage;
    }
}
