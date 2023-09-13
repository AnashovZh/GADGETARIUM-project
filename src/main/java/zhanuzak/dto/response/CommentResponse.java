package zhanuzak.dto.response;

import lombok.*;

import java.time.LocalDateTime;

//    private Long id;
//    private String comment;
//    private LocalDateTime createdDateTime;
@Builder
@Getter@Setter

public class CommentResponse {
    private Long id;
    private String comment;
    private LocalDateTime createDateTime;

    public CommentResponse(Long id, String comment, LocalDateTime createDateTime) {
        this.id = id;
        this.comment = comment;
        this.createDateTime = createDateTime;
    }

    public CommentResponse() {
    }
}
