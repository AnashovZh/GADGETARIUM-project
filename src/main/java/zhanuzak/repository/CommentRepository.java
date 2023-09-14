package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zhanuzak.dto.response.CommentResponse;
import zhanuzak.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select new zhanuzak.dto.response.CommentResponse(c.id,c.comment,c.createdDateTime)from Comment c")
    List<CommentResponse>findAllComments();
    @Query("select new zhanuzak.dto.response.CommentResponse(c.id,c.comment,c.createdDateTime)from Comment c where c.product.id=:productId")
    List<CommentResponse>findAllCommentsByProductId(Long productId);
}
