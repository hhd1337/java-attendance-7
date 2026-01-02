package attendance.controller;

import attendance.converter.StringToIntConverter;
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

    public LocalDate inputDateForMonth(LocalDate currentDate) {
        StringToIntConverter converter = new StringToIntConverter();
        return inputTemplate.execute(
                inputView::inputDateForMonth,
                value -> {
                    value = value.trim();
                    int updateDateForMonth = converter.convert(value);
                    validateDayOfMonthRange(updateDateForMonth, currentDate);
                    return LocalDate.of(currentDate.getYear(), currentDate.getMonth(), updateDateForMonth);
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

    // 현재날짜로 마지막일을 검증 (요구사항에 수정할 월을 받지 않음)
    // value가 현재 월에 포함되는 숫자인지(1~30,1~31,1~28) 검증함.
    private void validateDayOfMonthRange(int value, LocalDate currentDate) {
        LocalDate lastDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        if (value < 1 || value > lastDate.getDayOfMonth()) {
            throw new IllegalArgumentException("현재 월에 포함되는 날짜(일)를 입력해주세요.");
        }
    }

}