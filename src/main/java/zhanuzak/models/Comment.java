package zhanuzak.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zhanuzak.dto.request.CommentRequest;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seg")
    @SequenceGenerator(name = "comment_seg", allocationSize = 1)
    private Long id;
    private String comment;
    @Column(name = "create_date_time")
    private LocalDateTime createdDateTime;
    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;

    public static Comment build(CommentRequest commentRequest) {
        Comment comment1 = new Comment();
        comment1.setComment(commentRequest.comment());
        comment1.setCreatedDateTime(LocalDateTime.now());
        return comment1;

    }

}