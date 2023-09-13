package zhanuzak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import zhanuzak.dto.request.UserRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.enums.Country;
import zhanuzak.enums.Role;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.User;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.UserService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public SimpleResponse save(UserRequest userRequest) {
        log.info("first user save ");
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setCreatedDate(LocalDateTime.now());
        user.setRole(Role.USER);
        User save = userRepository.save(user);
        log.info("successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("User with id:" + save.getId() + " successfully saved ☺")
                .build();
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
        {
            log.info("not found!");
            return new NotFoundException("User with id :" + id + " not found !!!");
        });
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(passwordEncoder.encode(user.getPassword()));
        userResponse.setCreatedDate(user.getCreatedDate());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    @Override
    public SimpleResponse updateMap(Long id, Map<String, Object> fields) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "User with id:" + id + " not found !!!"));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                Object fieldValue = null;
                if (value instanceof String && field.getType() == String.class) {
                    fieldValue = value;
                } else if (value instanceof Country && field.getType() == Country.class) {
                    fieldValue = value;
                }
                if (fieldValue != null) {
                    user.setUpdatedDate(LocalDateTime.now());
                    ReflectionUtils.setField(field, user, value);
                }
            }
        });

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id:" + id + " successfully updated ☺")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        userRepository.deleteById(id);
        return SimpleResponse.builder()
                .message("User with id:" + id + " successfully deleted ☺")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteByEmailAndPassword(String email, String password) {
            User user = userRepository.findByEmailAndPassword(email, password);
            if (user != null) {
                userRepository.delete(user);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("User with email:" + email + " successfully deleted ☺")
                        .build();
            } else {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("User with email:" + email + " not found !!!")
                        .build();
            }
    }
}
