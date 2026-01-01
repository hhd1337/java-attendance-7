package attendance.controller;

import attendance.converter.StringToLocalDateTimeConverter;
import attendance.converter.StringToMenuConverter;
import attendance.domain.Crew;
import attendance.domain.CrewCatalog;
import attendance.domain.Holiday;
import attendance.domain.Menu;
import attendance.view.InputView;
import attendance.view.mapper.LocalDateTimeMapper;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class InputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate inputTemplate;

    public InputHandler(InputView inputView, IteratorInputTemplate inputTemplate) {
        this.inputView = inputView;
        this.inputTemplate = inputTemplate;
    }

    public Menu inputMenu() {
        StringToMenuConverter converter = new StringToMenuConverter();
        return inputTemplate.execute(
                inputView::inputMenu,
                value -> {
                    value = value.trim();
                    validateIsTodayAttendingDay(DateTimes.now().toLocalDate());
                    return converter.convert(value);
                }
        );
    }

    public Crew inputNickName(CrewCatalog crewCatalog) {
        return inputTemplate.execute(
                inputView::inputNickName,
                nickName -> {
                    nickName = nickName.trim();
                    return crewCatalog.findCrewByName(nickName);
                }
        );
    }

    public LocalDateTime inputArrivedTime(LocalDate currentDate) {
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();
        return inputTemplate.execute(
                inputView::inputArrivedTime,
                value -> {
                    value = value.trim();
                    LocalTime arrivedTime = converter.convertToTimeMinute(value);
                    validateIsTodayAttendingDay(currentDate);
                    return LocalDateTime.of(currentDate, arrivedTime);
                }
        );
    }

    // 토, 일, 공휴일 예외처리
    private void validateIsTodayAttendingDay(LocalDate currentDate) {
        DayOfWeek today = currentDate.getDayOfWeek();
        int dayNum = today.getValue();

        if (dayNum == 6 || dayNum == 7 || Holiday.findByDateOrNull(currentDate) != null) {
            LocalDateTimeMapper dateTimeToDayMapper = new LocalDateTimeMapper();
            String dayKor = dateTimeToDayMapper.mapLocalDateToDay(currentDate);
            int month = currentDate.getMonth().getValue();
            int date = currentDate.getDayOfMonth();

            throw new IllegalArgumentException(month + "월 " + date + "일 " + dayKor + "요일은 등교일이 아닙니다.");
        }
    }
}