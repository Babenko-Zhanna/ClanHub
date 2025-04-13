package ClanHub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class RegistRequestDto {
    private String username;
    private String email;
    private String password;
    private String avatarId;
    private Integer age;
}
