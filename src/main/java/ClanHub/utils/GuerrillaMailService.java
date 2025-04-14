package ClanHub.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class GuerrillaMailService {

    static Logger logger = LoggerFactory.getLogger(GuerrillaMailService.class);

    private static String createTemporaryMailbox() {

        RestAssured.baseURI = "https://www.guerrillamail.com";

        Map<String, String> formParams = new HashMap<>();
        formParams.put("email_user", "clanHubEmail");
        formParams.put("lang", "ru");
        formParams.put("site", "guerrillamail.com");
        formParams.put("in", "Установить отменить");

        Response response = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParams(formParams)
                .when()
                .post("/ajax.php?f=set_email_user")
                .then()
                .extract()
                .response();

        String sidToken = response.jsonPath().getString("sid_token");

        return sidToken;
    }

    public static String getResetCode() {

        String base_url = "https://api.guerrillamail.com/ajax.php";
        String sidToken = createTemporaryMailbox();
        int retry_count = 5;
        int delay_ms = 5000;

        for (int attempt = 0; attempt < retry_count; attempt++) {
            logger.info("🔁 Attempt #" + (attempt + 1));

            // 1. Получаем список писем
            Response listResponse = RestAssured
                    .given()
                    .queryParam("f", "get_email_list")
                    .queryParam("sid_token", sidToken)
                    .queryParam("offset", "0")
                    .get(base_url);

            List<Map<String, Object>> emails = listResponse.jsonPath().getList("list");

            if (emails != null && !emails.isEmpty()) {
                Optional<Map<String, Object>> resetEmailOpt = emails.stream()
                        .filter(email -> {
                            String subject = (String) email.get("mail_subject");
                            return subject != null && subject.toLowerCase().contains("password reset code");
                        })
                        .findFirst();

                if (resetEmailOpt.isPresent()) {
                    String mailId = (String) resetEmailOpt.get().get("mail_id");

                    // 2. Загружаем письмо
                    Response mailResponse = RestAssured
                            .given()
                            .queryParam("f", "fetch_email")
                            .queryParam("sid_token", sidToken)
                            .queryParam("email_id", mailId)
                            .get(base_url);

                    String body = mailResponse.jsonPath().getString("mail_body");

                    // 3. Ищем 6-значный код
                    Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
                    Matcher matcher = pattern.matcher(body);

                    String code = "";

                    if (matcher.find()) {
                        code = matcher.group();
                        logger.info("✅ Reset code was found: " + code);
                    }

                    deleteEmailsFromList(emails, sidToken);

                    return code;
                }
            }

            try {
                Thread.sleep(delay_ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }

        throw new RuntimeException("⛔ Reset code is not found after " + retry_count + " attempts");
    }

    public static void deleteEmailsFromList(List<Map<String, Object>> emails, String sidToken) {
        if (emails == null || emails.isEmpty()) {
            return;
        }

        var request = RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("sid_token", sidToken)
                .formParam("site", "guerrillamail.com")
                .formParam("in", "clanHubEmail");

        for (Map<String, Object> email : emails) {
            Object id = email.get("mail_id");
            if (id != null) {
                request = request.formParam("email_ids[]", id.toString());
            }
        }

        request.post("https://www.guerrillamail.com/ajax.php?f=del_email");
    }
}

