package zhanuzak.service;

import zhanuzak.dto.request.UserRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserResponse> findAll();

    SimpleResponse save(UserRequest userRequest);

    UserResponse findById(Long id);

    SimpleResponse updateMap(Long id, Map<String, Object> fields);

    SimpleResponse deleteById(Long id);

    SimpleResponse deleteByEmailAndPassword(String email, String password);
}


