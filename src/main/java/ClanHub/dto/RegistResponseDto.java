package ClanHub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class RegistResponseDto {
    private UserResponseDto user;
    private String token;
    private String message;
    private String status;
}
