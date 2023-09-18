package zhanuzak.dto.response;

import lombok.*;
import zhanuzak.enums.Role;

import java.time.LocalDate;
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
}
