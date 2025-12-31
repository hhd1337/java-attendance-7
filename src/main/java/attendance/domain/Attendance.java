package attendance.domain;

import static attendance.domain.DayAducationTime.MON;
import static attendance.domain.DayAducationTime.TUES_TO_FRI;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    private String crewName;
    List<LocalDateTime> attendanceHistory;

    private int lateCount;
    private int actualAbsenceCount; // 순수 실제 지각횟수 : 지각 3회 결석1회로 보지 않음
    private int calculatedAbsenceCount; // 순지각 3회 결석1회로 본 결석 횟수

    public Attendance(String crewName, List<LocalDateTime> attendanceHistory) {
        this.crewName = crewName;
        this.attendanceHistory = attendanceHistory;
        calculateLateAndAbsence(attendanceHistory);
    }

    public void calculateLateAndAbsence(List<LocalDateTime> attendanceHistory) {
        this.lateCount = 0;
        this.actualAbsenceCount = 0;
        this.calculatedAbsenceCount = 0;

        attendanceHistory.forEach(dateTime -> {
            DayOfWeek day = dateTime.getDayOfWeek();
            int dayNum = day.getValue();
            LocalTime time = dateTime.toLocalTime();

            if (dayNum == 1) { // 월요일
                if (isCrewLate(MON, time)) {
                    this.lateCount++;
                }
                if (isCrewAbsent(MON, time)) {
                    this.actualAbsenceCount++;
                }
            }
            if (dayNum >= 2 && dayNum <= 5) { // 화~금요일
                if (isCrewLate(TUES_TO_FRI, time)) {
                    this.lateCount++;
                }
                if (isCrewAbsent(TUES_TO_FRI, time)) {
                    this.actualAbsenceCount++;
                }
            }
        });
        calculatedAbsenceCount = actualAbsenceCount + (lateCount % 3);
    }

    private boolean isCrewLate(DayAducationTime day, LocalTime time) {
        return time.isAfter(day.getStartTime().plusMinutes(5)) && time.isBefore(day.getEndTime()); // 시작시간 5분뒤에 왔는지
    }

    private boolean isCrewAbsent(DayAducationTime day, LocalTime time) {
        return time.isAfter(day.getEndTime()); // 종료시간 이후에 왔는지
    }
}
