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

public class DataProvidersGUI {

    @DataProvider
    public Iterator<Object[]> registrationInvalidUsernameFromCsv() throws IOException {
        List<Object[]> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/invalid_usernameForGui.csv"));

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withQuote('"')
                .parse(reader);

        for (CSVRecord record : records) {

            String username = record.get(0);
            String validationMessage = record.get(1);

            list.add(new Object[]{username, validationMessage});
        }
        reader.close();
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> registrationInvalidAgeDataProvider() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"150"});
        list.add(new Object[]{"4"});
        list.add(new Object[]{"101"});
        list.add(new Object[]{"-10"});
        list.add(new Object[]{"-100500"});
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> registrationInvalidEmailDataProvider() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"test@.com"});
        list.add(new Object[]{"test@gmail."});
        list.add(new Object[]{"test@gmailcom"});
        list.add(new Object[]{"testgmail.com"});
        list.add(new Object[]{"Fghjkkjjgf"});
        list.add(new Object[]{"жвылоажф"});
        list.add(new Object[]{"SELECT* FROM"});
        list.add(new Object[]{"test @gmail.com"});
        list.add(new Object[]{"0.00000000000"});
        list.add(new Object[]{"<script></script>"});
        list.add(new Object[]{"http://www.telran.com/"});
        return list.iterator();
    }

    @DataProvider
    public Iterator<Object[]> registrationInvalidPasswordDataProvider() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{"Hh1234$", "Password must be at least 8 characters"});
        list.add(new Object[]{"Hh1#", "Password must be at least 8 characters"});
        list.add(new Object[]{"Hh1234$Hh12345678901234567890987$", "Password must not exceed 25 characters"});
        list.add(new Object[]{"HH12345$", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"hh12345$", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"Hh123456", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"Hh!@#$%^", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"1234567$", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"SELECT*FROM", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"test@gmail.com", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"<script></script>", "Password must include uppercase, lowercase, number and special character"});
        list.add(new Object[]{"Дылаофж5$", "Only Latin letters, numbers and symbols are allowed"});
        return list.iterator();
    }
}
