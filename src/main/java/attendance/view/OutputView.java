package attendance.view;

import attendance.util.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class OutputView {

    public void printErrorMessage(Exception exception) {
        System.out.println(ErrorMessage.PREFIX + exception.getMessage());
    }

    public void printHelloAndMenu(LocalDateTime dateTime) {
        String day = mapLocalDateTimeToDay(dateTime);
        System.out.println(
                "오늘은 " + dateTime.getMonth().getValue() + "월 " + dateTime.getDayOfMonth() + "일 " + day
                        + "요일입니다. 기능을 선택해주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    private String mapLocalDateTimeToDay(LocalDateTime dateTime) {
        String[] day = {"월", "화", "수", "목", "금", "토", "일"};
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        int dayNum = dayOfWeek.getValue();

        return day[dayNum - 1];
    }
}
