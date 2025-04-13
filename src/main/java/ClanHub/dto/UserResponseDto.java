package ClanHub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UserResponseDto {
    private String id;
    private String username;
    private String email;
    private RoleResponseDto role;
    private String avatarId;
    private int age;
}
