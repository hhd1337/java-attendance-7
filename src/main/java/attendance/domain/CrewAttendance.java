package attendance.domain;

import static attendance.domain.AttendanceState.ABSENCE;
import static attendance.domain.AttendanceState.LATE;
import static attendance.domain.AttendanceState.SUCCESS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CrewAttendance {
    private String crewName;
    private final List<LocalDateTime> attendanceHistory;

    private int successCount;
    private int lateCount;
    private int actualAbsenceCount; // 순수 실제 지각횟수 : 지각 3회 결석1회로 보지 않음
    private int calculatedAbsenceCount; // 순지각 3회 결석1회로 본 결석 횟수

    public CrewAttendance(String crewName, List<LocalDateTime> attendanceHistory, LocalDateTime currDateTime) {
        this.crewName = crewName;
        this.attendanceHistory = attendanceHistory;
        calculateAndSetCounts(currDateTime);
    }

    public void calculateAndSetCounts(LocalDateTime currDateTime) {
        initCountsToZero(); // 0으로 이 크루의 counts 다 초기화
        // 1일부터 어제날짜까지 순회
        for (LocalDate indexDate = currDateTime.toLocalDate().withDayOfMonth(1);
             indexDate.isBefore(currDateTime.toLocalDate());
             indexDate = indexDate.plusDays(1)) {

            // date가 HOLIDAY || WEEKEND 일때는 넘어감.
            if (AttendanceTimeRule.from(indexDate) == AttendanceTimeRule.HOLIDAY
                    || AttendanceTimeRule.from(indexDate) == AttendanceTimeRule.WEEKEND) {
                continue;
            }

            // 당일 출석기록 찾음
            LocalDateTime indexDateTime = findDateTimeByDateOrNull(indexDate);

            // 해당 크루의 ActualAbsenceCount 증가
            if (indexDateTime == null) {
                increaseActualAbsenceCount();
            }
            // 이번날짜(indexDateTime)가 출석부에 있으면 각 상태에 따라 증가시킴
            if (indexDateTime != null) {
                AttendanceState attendanceState = AttendanceTimeRule.from(indexDate)
                        .judgeAttendance(indexDateTime.toLocalTime());
                if (attendanceState == SUCCESS) {
                    increaseSuccessCount();
                }
                if (attendanceState == LATE) {
                    increaseLateCount();
                }
                if (attendanceState == ABSENCE) {
                    increaseActualAbsenceCount();
                }
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
                // calculateTotalLateAndAbsence();
                return;
            }
        }
        throw new IllegalArgumentException("수정하려는 날짜에 해당 크루의 원래 출석기록이 없습니다.");
    }

    public LocalDateTime findDateTimeByDateOrNull(LocalDate updateDate) {
        try {
            return attendanceHistory.stream()
                    .filter(unit -> unit.toLocalDate().isEqual(updateDate))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 해당 크루의 원래 출석기록이 없습니다."));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void increaseSuccessCount() {
        this.successCount++;
    }

    public void increaseLateCount() {
        this.lateCount++;
    }

    public void increaseActualAbsenceCount() {
        this.actualAbsenceCount++;
    }

    public void initCountsToZero() {
        this.successCount = 0;
        this.lateCount = 0;
        this.actualAbsenceCount = 0;
        this.calculatedAbsenceCount = 0;
    }

    public String getCrewName() {
        return this.crewName;
    }

    public List<LocalDateTime> getAttendanceHistory() {
        return this.attendanceHistory;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public int getLateCount() {
        return this.lateCount;
    }

    public int getActualAbsenceCount() {
        return this.actualAbsenceCount;
    }

    public int getCalculatedAbsenceCount() {
        calculatedAbsenceCount = actualAbsenceCount + (lateCount / 3);
        return calculatedAbsenceCount;
    }

}
