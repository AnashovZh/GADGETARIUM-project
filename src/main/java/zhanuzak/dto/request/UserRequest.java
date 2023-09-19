package zhanuzak.dto.request;

import lombok.Builder;

//private Long id;
//private String firstName;
//private String lastName;
//private String email;
//private String password;
//private LocalDate createdDate;
//private LocalDate updatedDate;
//private Role role;
@Builder
public record UserRequest(String firstName, String lastName,
                          String email, String password) {
}
