package ClanHub.data;

import ClanHub.dto.RegistRequestDto;

import java.util.UUID;

public class RegistrData {

    public static final String VALID_USERNAME = "username123";
    public static final String VALID_AVATARID = "avatar1";
    public static final int VALID_AGE = 22;
    public static final String VALID_PASSWORD = "Password1#";

    public static RegistRequestDto emptyEmailUser = RegistRequestDto.builder()
            .username("username1")
            .password("Password1#")
            .avatarId("avatar1")
            .age(15)
            .build();

    public static RegistRequestDto emptyUsernameUser = RegistRequestDto.builder()
            .email(generateRandomEmail())
            .password("Password1#")
            .avatarId("avatar1")
            .age(15)
            .build();

    public static RegistRequestDto emptyPasswordUser = RegistRequestDto.builder()
            .username("username2")
            .email(generateRandomEmail())
            .avatarId("avatar1")
            .age(15)
            .build();

    public static RegistRequestDto emptyAvatarUser = RegistRequestDto.builder()
            .username("username3")
            .email(generateRandomEmail())
            .password("Password1#")
            .age(15)
            .build();

    public static RegistRequestDto emptyAgeUser = RegistRequestDto.builder()
            .username("username3")
            .email(generateRandomEmail())
            .password("Password1#")
            .avatarId("avatar1")
            .build();


    public static RegistRequestDto validBody = RegistRequestDto.builder()
            .username("username123")
            .email(generateRandomEmail())
            .password("Password1#")
            .avatarId("avatar5")
            .age(32)
            .build();

    public static String generateRandomEmail() {
        return  "user" + UUID.randomUUID().toString().substring(0, 8) + "@test.qa";
    }
}
