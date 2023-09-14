package zhanuzak.service;

import zhanuzak.dto.request.CommentRequest;
import zhanuzak.dto.response.CommentResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> findAll();

    SimpleResponse update(Long id, CommentRequest commentRequest);

    CommentResponse findById(Long id);

    SimpleResponse delete(Long id);

    SimpleResponse saveComment(Long productId, CommentRequest commentRequest);

    List<CommentResponse> findAllProductId(Long id);
}
