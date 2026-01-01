package attendance.view.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeToDayMapper {
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
}
