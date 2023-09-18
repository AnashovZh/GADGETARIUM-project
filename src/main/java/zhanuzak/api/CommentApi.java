package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.CommentRequest;
import zhanuzak.dto.response.CommentResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PermitAll
    @GetMapping
    List<CommentResponse> getAll() {
        return commentService.findAll();
    }

    @PermitAll
    @GetMapping("/getAllByProductId/{id}")
    List<CommentResponse> getAllWithProductId(@PathVariable Long id) {
        return commentService.findAllProductId(id);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{productId}/save")
    SimpleResponse saveComment(@PathVariable Long productId,
                               @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(productId, commentRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    CommentResponse getById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateCommentToProduct(@PathVariable Long id,
                                          @RequestBody CommentRequest commentRequest) {
        return commentService.update(id, commentRequest);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse delete(@PathVariable Long id) {
        return commentService.delete(id);
    }


}
