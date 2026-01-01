package attendance.view.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeMapper {
    public String mapLocalDateTimeToDay(LocalDateTime dateTime) {
        String[] day = {"월", "화", "수", "목", "금", "토", "일"};
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        int dayNum = dayOfWeek.getValue();

        return day[dayNum - 1];
    }

    public String mapLocalDateToDay(LocalDate date) {
        String[] day = {"월", "화", "수", "목", "금", "토", "일"};
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayNum = dayOfWeek.getValue();

        return day[dayNum - 1];
    }

    // 12월 13일 금요일 포맷의 String 반환
    public String mapDateTimeToAttendResultFormat(LocalDateTime dateTime) {
        String[] day = {"월", "화", "수", "목", "금", "토", "일"};

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        int dayNum = dayOfWeek.getValue();
        String dayKor = day[dayNum - 1];

        // 12월 13일 금요일 09:59 (출석)
        String localDateTimeFormat = dateTime.format(
                DateTimeFormatter.ofPattern("MM월 dd일 " + dayKor + "요일" + " HH:mm"));

        return localDateTimeFormat;
    }
}
