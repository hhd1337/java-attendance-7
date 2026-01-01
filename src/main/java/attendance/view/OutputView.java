package attendance.view;

import attendance.util.ErrorMessage;
import attendance.view.mapper.LocalDateTimeToDayMapper;
import java.time.LocalDateTime;

public class OutputView {

    LocalDateTimeToDayMapper dayMapper = new LocalDateTimeToDayMapper();

    public void printErrorMessage(Exception exception) {
        System.out.println(ErrorMessage.PREFIX + exception.getMessage());
    }

    public void printHelloAndMenu(LocalDateTime dateTime) {
        String day = dayMapper.mapLocalDateTimeToDay(dateTime);
        System.out.println(
                "오늘은 " + dateTime.getMonth().getValue() + "월 " + dateTime.getDayOfMonth() + "일 " + day
                        + "요일입니다. 기능을 선택해주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    public void printNickNameInputPrompt() {
        System.out.println("닉네임을 입력해 주세요.");
    }

    public void printArrivedTimeInputPrompt() {
        System.out.println("등교 시간을 입력해 주세요.");
    }
}
