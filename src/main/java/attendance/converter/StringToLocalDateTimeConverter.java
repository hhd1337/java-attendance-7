package attendance.converter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalDateTime convert(String value) {
        return convertToMinute(value);
    }

    public LocalDateTime convertToMinute(String value) {
        String trimmed = validateAndTrim(value);
        try {
            return LocalDateTime.parse(trimmed, MINUTE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
    }

    public LocalTime convertToTimeMinute(String value) {
        String trimmed = validateAndTrim(value);
        try {
            return LocalTime.parse(trimmed, TIME_MINUTE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
        }
    }

    private String validateAndTrim(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("날짜/시간 입력이 비어있습니다.");
        }
        return value.trim();
    }
}