package helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class Helpers {

    public static int fibbonachiCalc() {
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        int fibbonachiNumber = dayOfMonth + 1;

        int previous = 0, current = 1;
        for (int i = 3; i <= fibbonachiNumber; i++) {
            int temp = current;
            current = previous + current;
            previous = temp;
        }
        return current;
    }

    public static String formatDateTime(String text) {
        try {
            DateTimeFormatter inputFormatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("MMM dd, yyyy h:mm:ss a")
                    .toFormatter(Locale.ENGLISH);
            DateTimeFormatter outputFormatter = new DateTimeFormatterBuilder()
                    .appendPattern("dd MMMM yyyy HH:mm:ss")
                    .toFormatter(Locale.ENGLISH);
            LocalDateTime dateTime = LocalDateTime.parse(text, inputFormatter);
            return capitalizeFirstLetters(dateTime.format(outputFormatter));
        } catch (Exception e) {
            return text;
        }
    }

    public static String capitalizeFirstLetters(String str) {
        StringBuilder sb = new StringBuilder();
        String[] words = str.split(" ");
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
