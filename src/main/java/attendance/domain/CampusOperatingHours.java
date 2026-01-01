package attendance.domain;

import java.time.LocalTime;

public enum CampusOperatingHours {
    DEFAULT(LocalTime.of(13, 0), LocalTime.of(18, 0));

    private final LocalTime start;
    private final LocalTime end;

    CampusOperatingHours(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public void validate(LocalTime time) {
        if (time.isBefore(start) || time.isAfter(end)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    public LocalTime getStart() {
        return this.start;
    }

    public LocalTime getEnd() {
        return this.end;
    }
}
