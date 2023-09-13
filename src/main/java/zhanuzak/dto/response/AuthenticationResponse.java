package zhanuzak.dto.response;

import lombok.Builder;
import zhanuzak.enums.Role;
@Builder
public record AuthenticationResponse(String token, String email, Role role) {
}
