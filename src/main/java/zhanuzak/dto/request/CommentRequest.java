package zhanuzak.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentRequest(String comment, LocalDateTime createdDateTime) {

}
