package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;
import zhanuzak.enums.Role;
import zhanuzak.models.User;

import java.time.LocalDateTime;

//private Long id;
//private String firstName;
//private String lastName;
//private String email;
//private String password;
//private LocalDate createdDate;
//private LocalDate updatedDate;
//private Role role;

@Getter@Setter
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private Role role;


    public UserResponse(String firstName, String lastName, String email,
                        String password, LocalDateTime createdDate, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.role = role;
    }

    public UserResponse() {
    }
    public UserResponse build(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setCreatedDate(user.getCreatedDate());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}
