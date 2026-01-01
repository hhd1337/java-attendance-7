package attendance.domain;

public enum AttendanceState {
    SUCCESS("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String korState;

    AttendanceState(String korState) {
        this.korState = korState;
    }

    public String getKorState() {
        return this.korState;
    }
}
