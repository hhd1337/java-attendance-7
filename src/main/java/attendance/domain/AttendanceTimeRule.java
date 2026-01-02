package attendance.domain;

import static attendance.domain.Holiday.CHRISTMAS;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceTimeRule {
    MON(LocalTime.of(13, 0), LocalTime.of(18, 0)),
    TUES_TO_FRI(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    WEEKEND(null, null),
    HOLIDAY(null, null);

    private LocalTime startTime;
    private LocalTime endTime;

    AttendanceTimeRule(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AttendanceState judgeAttendance(LocalTime time) {
        if (isCrewLate(time)) {
            return AttendanceState.LATE;
        }
        if (isCrewAbsent(time)) {
            return AttendanceState.ABSENCE;
        }
        return AttendanceState.SUCCESS;
    }

    private boolean isCrewLate(LocalTime time) {
        return time.isAfter(this.startTime.plusMinutes(5)) &&
                time.isBefore(this.startTime.plusMinutes(30)); // 교육시작시간 5분뒤부터, 30분전에 왔는지
    }

    // 교육시작시간 30분 뒤부터 결석으로 간주
    private boolean isCrewAbsent(LocalTime time) {
        return time.isAfter(this.startTime.plusMinutes(30)) && time.isBefore(
                CampusOperatingHours.DEFAULT.getEnd()); // 교육시간 이후, 운영시간 전에 왔는지
    }

    public static AttendanceTimeRule from(LocalDate date) {
        int dayInt = date.getDayOfWeek().getValue();
        if (date.isEqual(CHRISTMAS.getDate())) {
            return HOLIDAY;
        }
        if (dayInt == 1) { // 월요일일 경우
            return MON;
        }
        if (dayInt >= 2 && dayInt <= 5) {
            return TUES_TO_FRI;
        }
        if (dayInt == 6 || dayInt == 7) {
            return WEEKEND;
        }
        throw new IllegalArgumentException("해당 날짜로 요일을 판단할 수 없습니다.");
    }
}
