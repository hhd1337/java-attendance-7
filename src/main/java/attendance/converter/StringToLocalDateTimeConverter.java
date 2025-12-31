package attendance.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime convert(String value) {
        return convertToMinute(value);
    }

    public LocalDateTime convertToMinute(String value) {
        String trimmed = validateAndTrim(value);
        try {
            return LocalDateTime.parse(trimmed, MINUTE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜/시간(분) 형식이 올바르지 않습니다. 예) 2025-01-10 10:06");
        }
    }

    private String validateAndTrim(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("날짜/시간 입력이 비어있습니다.");
        }
        return value.trim();
    }
}