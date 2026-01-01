package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(LocalDate.of(2024, 12, 25));

    private final LocalDate date;

    Holiday(LocalDate date) {
        this.date = date;
    }

    public static Holiday findByDate(LocalDate date) {
        return Arrays.stream(Holiday.values())
                .filter(day -> day.getDate() == date)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }

    public static Holiday findByDateOrNull(LocalDate date) {
        try {
            return Arrays.stream(Holiday.values())
                    .filter(day -> day.getDate() == date)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public LocalDate getDate() {
        return this.date;
    }

}
