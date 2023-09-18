package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.CommentResponse;
import zhanuzak.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select new zhanuzak.dto.response.CommentResponse(c.id,c.comment,c.createdDateTime)from Comment c")
    List<CommentResponse> findAllComments();

    @Query("select new zhanuzak.dto.response.CommentResponse(c.id,c.comment,c.createdDateTime)from Comment c where c.product.id=:productId")
    List<CommentResponse> findAllCommentsByProductId(Long productId);

    @Query("select concat( u.firstName,' :', c.comment) from Comment c join User  u on c.user.id=u.id where c.product.id=?1")
    List<String> comments(Long id);

    @Query("select count(c.product.id)from Product p join Comment c on c.product.id=p.id where p.id=?1")
    int countComments(Long id);

    @Query("select  count(distinct (c.user.id)) from Comment  c join Product p on c.product.id=p.id where p.id=?1 ")
    int countCommentsByUser(Long id);

    @Query("SELECT concat( 'User with name:',u.firstName,' wrote ', COUNT(c.id),' comments ☺')" +
            "FROM User u\n" +
            "LEFT JOIN Comment c ON c.user.id = u.id\n" +
            "WHERE c.product.id = ?1\n" +
            "GROUP BY u.id,u.firstName\n" +
            "HAVING COUNT(c.id) > 0")
    List<String> getFirstNameCommentsByUser(Long id);

@Query("select count (p.isFavorite) from Product  p where p.id=?1 and p.isFavorite=true ")
    int countLike(Long id);
}
