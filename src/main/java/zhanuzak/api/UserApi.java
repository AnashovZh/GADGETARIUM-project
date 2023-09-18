package zhanuzak.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.UserRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "UserApi")
public class UserApi {
    private final UserService userService;

    @PermitAll
    @GetMapping
    List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    SimpleResponse save(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @GetMapping("/{id}")
    @PermitAll
    UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse updateMap(@PathVariable Long id,
                             @RequestBody Map<String, Object> fields) {
        return userService.updateMap(id, fields);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteByEmailAndPassword(@RequestParam String email,
                                            @RequestParam String password) {
        return userService.deleteByEmailAndPassword(email, password);
    }
}
