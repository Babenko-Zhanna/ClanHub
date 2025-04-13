package ClanHub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class RoleResponseDto {
    private String id;
    private String roleName;
}
