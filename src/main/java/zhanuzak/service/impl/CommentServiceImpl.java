package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.request.CommentRequest;
import zhanuzak.dto.response.CommentResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Comment;
import zhanuzak.models.Product;
import zhanuzak.models.User;
import zhanuzak.repository.CommentRepository;
import zhanuzak.repository.ProductRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.CommentService;

import java.rmi.NotBoundException;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CommentResponse> findAll() {
        return commentRepository.findAllComments();
    }

    @Override
    public SimpleResponse update(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id:" + id + " not found!!!"));
        comment.setComment(commentRequest.comment());
        comment.setUpdatedDateTime(LocalDateTime.now());
        Comment save = commentRepository.save(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment successfully saved ☺ with id:" + save.getId())
                .build();
    }

    @Override
    public CommentResponse findById(Long id) {
        if (!userRepository.existsById(id)) {
            log.error("\"Comment with id:\" + id + \" not found!!!\"");
            throw new NotFoundException("Comment with id:" + id + " not found!!!");

        }
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id:" + id + " not found !!!"));
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCreateDateTime(comment.getCreatedDateTime());
        return commentResponse;
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Comment successfully deleted ☺ with id:" + id)
                    .build();
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Comment with id:" + id + " not found !!!")
                    .build();
        }
    }

    @Override
    public SimpleResponse saveComment(Long productId, CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id :" + productId + " not found !!!"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setProduct(product);
        comment.setUser(user);
        user.addComment(comment);
        Comment save = commentRepository.save(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment successfully saved ☺ with id:" + save.getId())
                .build();
    }
}
