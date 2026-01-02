package attendance.view;

import attendance.util.ErrorMessage;
import attendance.view.mapper.LocalDateTimeMapper;
import java.time.LocalDateTime;

public class OutputView {

    LocalDateTimeMapper mapper = new LocalDateTimeMapper();

    public void printErrorMessage(Exception exception) {
        System.out.println(ErrorMessage.PREFIX + exception.getMessage());
    }

    public void printHelloAndMenu(LocalDateTime dateTime) {
        String day = mapper.mapLocalDateTimeToDay(dateTime);
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

    // 오늘 날짜, 요일, 출석시간, 출석결과(출석,지각,결석)를 출력한다.
    // 12월 13일 금요일 09:59 (출석)
    public void printAttendResultPrompt(LocalDateTime dateTime, String AttendStatus) {
        String prompt = mapper.mapDateTimeToAttendResultFormat(dateTime);
        System.out.println(prompt + " (" + AttendStatus + ")");
    }

    public void printNickNameInputForUpdatePrompt() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public void printDateForMonthInputPrompt() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public void printUpdateTimeInputPrompt() {
        System.out.println("언제로 변경하겠습니까?");
    }
}