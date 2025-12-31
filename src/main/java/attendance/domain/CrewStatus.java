package attendance.domain;

import java.util.Arrays;

public enum CrewStatus {
    GOOD(0, 1),
    WARN(2, 2), // 2만
    INTERVIEW(3, 5), // 3부터 5까지
    WEEDING(6, Integer.MAX_VALUE); // 6 이상

    private final Integer absenceCountFrom;
    private final Integer absenceCountTo;

    CrewStatus(Integer absenceCountFrom, Integer absenceCountTo) {
        this.absenceCountFrom = absenceCountFrom;
        this.absenceCountTo = absenceCountTo;
    }

    public static CrewStatus findByAbsenceCount(Integer count) {
        return Arrays.stream(CrewStatus.values())
                .filter(status -> status.contains(count))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }

    private boolean contains(int count) {
        return this.absenceCountFrom <= count && this.absenceCountTo >= count;
    }

    public Integer getAbsenceCountFrom() {
        return this.absenceCountFrom;
    }

    public Integer getAbsenceCountTo() {
        return this.absenceCountTo;
    }
}
