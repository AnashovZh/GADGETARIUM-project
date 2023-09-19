package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter


public class BasketResponse {
    private Long id;
    private String fullName;
    private List<String> productNames;

    public BasketResponse(Long id) {
        this.id = id;
    }

    public BasketResponse() {
    }


}
