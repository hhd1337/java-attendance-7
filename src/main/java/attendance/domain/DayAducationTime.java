package attendance.domain;

import java.time.LocalTime;

public enum DayAducationTime {
    MON("13:00", "18:00"),
    TUES_TO_FRI("10:00", "18:00"),
    WEEKEND(null, null),
    HOLIDAY(null, null);

    private LocalTime startTime;
    private LocalTime endTime;

    DayAducationTime(String startTime, String endTime) {
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }
}
