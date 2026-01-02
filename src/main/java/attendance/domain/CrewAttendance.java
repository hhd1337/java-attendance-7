package attendance.domain;

import static attendance.domain.AttendanceTimeRule.MON;
import static attendance.domain.AttendanceTimeRule.TUES_TO_FRI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CrewAttendance {
    private String crewName;
    private List<LocalDateTime> attendanceHistory;

    private int lateCount;
    private int actualAbsenceCount; // 순수 실제 지각횟수 : 지각 3회 결석1회로 보지 않음
    private int calculatedAbsenceCount; // 순지각 3회 결석1회로 본 결석 횟수

    public CrewAttendance(String crewName, List<LocalDateTime> attendanceHistory) {
        this.crewName = crewName;
        this.attendanceHistory = attendanceHistory;
        calculateTotalLateAndAbsence(attendanceHistory);
    }

    public void calculateTotalLateAndAbsence(List<LocalDateTime> attendanceHistory) {
        this.lateCount = 0;
        this.actualAbsenceCount = 0;
        this.calculatedAbsenceCount = 0;

        attendanceHistory.forEach(this::calculateLateAndAbsence);
        calculatedAbsenceCount = actualAbsenceCount + (lateCount / 3);
    }

    public void calculateLateAndAbsence(LocalDateTime dateTime) {
        DayOfWeek day = dateTime.getDayOfWeek();
        int dayNum = day.getValue();
        LocalTime time = dateTime.toLocalTime();
        AttendanceState judgedAttendanceState;

        if (dayNum == 1) { // 월요일
            judgedAttendanceState = MON.judgeAttendance(time);
            if (judgedAttendanceState == AttendanceState.LATE) {
                this.lateCount++;
            }
            if (judgedAttendanceState == AttendanceState.ABSENCE) {
                this.actualAbsenceCount++;
            }
        }
        if (dayNum >= 2 && dayNum <= 5) { // 화~금요일
            judgedAttendanceState = TUES_TO_FRI.judgeAttendance(time);
            if (judgedAttendanceState == AttendanceState.LATE) {
                this.lateCount++;
            }
            if (judgedAttendanceState == AttendanceState.ABSENCE) {
                this.actualAbsenceCount++;
            }
        }
    }

    public void addAttendance(LocalDateTime dateTime) {
        attendanceHistory.add(dateTime);
    }

    public void updateAttendance(LocalDateTime newDateTime) {
        for (int index = 0; index < attendanceHistory.size(); index++) {
            // newDateTime과 날짜가 같은 레코드를 newDateTime 으로 치환
            if (attendanceHistory.get(index).toLocalDate().isEqual(newDateTime.toLocalDate())) {
                attendanceHistory.set(index, newDateTime);
                calculateTotalLateAndAbsence(attendanceHistory);
                return;
            }
        }
        throw new IllegalArgumentException("수정하려는 날짜에 해당 크루의 원래 출석기록이 없습니다.");
    }

    public String getCrewName() {
        return this.crewName;
    }

    public LocalDateTime findDateTimeByDateOrNull(LocalDate updateDate) {
        return attendanceHistory.stream()
                .filter(unit -> unit.toLocalDate().isEqual(updateDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 해당 크루의 원래 출석기록이 없습니다."));
    }
}
