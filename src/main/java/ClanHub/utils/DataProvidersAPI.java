package ClanHub.utils;

import ClanHub.data.RegistrData;
import ClanHub.dto.RegistRequestDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class DataProvidersAPI {


    @DataProvider
    public Iterator<RegistRequestDto> registrationValidUserDataFromCsv() throws IOException {
        List<RegistRequestDto> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/valid_users.csv"));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",");
            String email = "user" + UUID.randomUUID().toString().substring(0, 28) + "@test.qa";;
            list.add(RegistRequestDto.builder()
                    .username(split[0])
                    .email(email)
                    .password(split[1])
                    .avatarId(split[2])
                    .age(Integer.parseInt(split[3]))
                    .build()
            );
            line = reader.readLine();
        }
        reader.close();
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> registrationInvalidUsernameAPIFromCsv() throws IOException {
        List<Object[]> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/invalid_usernameForApi.csv"));

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withQuote('"')
                .parse(reader);

        for (CSVRecord record : records) {

            RegistRequestDto user = RegistRequestDto.builder()
                    .username(record.get(0))
                    .email(RegistrData.generateRandomEmail())
                    .password("Password1#")
                    .avatarId("avatar4")
                    .age(22)
                    .build();

            list.add(new Object[]{user, record.get(1)});
        }
        reader.close();
        return list.iterator();
    }

    @DataProvider
    public Iterator<RegistRequestDto> registrationInvalidEmailFromCsv() throws IOException {
        List<RegistRequestDto> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/invalid_email.csv"));
        String line = reader.readLine();
        while (line != null) {
            list.add(RegistRequestDto.builder()
                    .username(RegistrData.VALID_USERNAME)
                    .email(line)
                    .password(RegistrData.VALID_PASSWORD)
                    .avatarId(RegistrData.VALID_AVATARID)
                    .age(RegistrData.VALID_AGE)
                    .build()
            );
            line = reader.readLine();
        }
        reader.close();
        return list.iterator();
    }

    @DataProvider
    public Iterator<RegistRequestDto> registrationInvalidAvatarIdFromCsv() throws IOException {
        List<RegistRequestDto> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/invalid_avatarId.csv"));
        String line = reader.readLine();
        while (line != null) {
            list.add(RegistRequestDto.builder()
                    .username(RegistrData.VALID_USERNAME)
                    .email(RegistrData.generateRandomEmail())
                    .password(RegistrData.VALID_PASSWORD)
                    .avatarId(line)
                    .age(RegistrData.VALID_AGE)
                    .build()
            );
            line = reader.readLine();
        }
        reader.close();
        return list.iterator();
    }

    @DataProvider
    public Object[][] registrationInvalidPasswordDataProvider() {
        String username = RegistrData.VALID_USERNAME;
        String email = RegistrData.generateRandomEmail();
        String avatarId = RegistrData.VALID_AVATARID;
        int age = RegistrData.VALID_AGE;

        return new Object[][]{
                {RegistRequestDto.builder().username(username).email(email).password("Hh1234$").avatarId(avatarId).age(age).build(), "Password should have at least 8 symbols"},
                {RegistRequestDto.builder().username(username).email(email).password("Hh1#").avatarId(avatarId).age(age).build(), "Password should have at least 8 symbols"},
                {RegistRequestDto.builder().username(username).email(email).password("Hh1234$Hh12345678901234567890987$").avatarId(avatarId).age(age).build(), "Password can't be longer than 25 symbols"},
                {RegistRequestDto.builder().username(username).email(email).password("HH12345$").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("hh12345$").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("Hh123456").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("Hh!@#$%^").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("1234567$").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("Дылаофж5$").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("SELECT*FROM").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("test@gmail.com").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("<script></script>").avatarId(avatarId).age(age).build(), "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"},
                {RegistRequestDto.builder().username(username).email(email).password("Hhhhhhhhhhh12345678901234567890987$").avatarId(avatarId).age(age).build(), "Password can't be longer than 25 symbols"}
        };
    }

    @DataProvider
    public Object[][] registrationInvalidAgeDataProvider() {
        String username = RegistrData.VALID_USERNAME;
        String email = RegistrData.generateRandomEmail();
        String avatarId = RegistrData.VALID_AVATARID;
        String password = RegistrData.VALID_PASSWORD;

        return new Object[][]{
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(4).build(), "Age must be at least 5"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(0).build(), "Age must be at least 5"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(-10).build(), "Age must be at least 5"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(-100500).build(), "Age must be at least 5"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(101).build(), "Age must not exceed 100"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(150).build(), "Age must not exceed 100"},
                {RegistRequestDto.builder().username(username).email(email).password(password).avatarId(avatarId).age(9999999).build(), "Age must not exceed 100"}
        };
    }
}
