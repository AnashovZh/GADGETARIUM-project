package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zhanuzak.dto.response.UserResponse;
import zhanuzak.models.User;

import java.util.List;
import java.util.Optional;
//  private Long id;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String password;
//    private LocalDate createdDate;
//    private Role role;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query("select new zhanuzak.dto.response.UserResponse(u.firstName, u.lastName, u.email, u.password, u.createdDate, u.role) from User u")
    List<UserResponse> findAllUsers();

    User findByEmailAndPassword(String email, String password);

    Optional<User> getUserByEmail(String email);
}
