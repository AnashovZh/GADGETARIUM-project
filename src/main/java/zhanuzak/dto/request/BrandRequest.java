package zhanuzak.dto.request;

import lombok.Builder;

@Builder
public record BrandRequest(String brandName,String brandImage) {
}
