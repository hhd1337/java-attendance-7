package attendance.domain;

import attendance.util.ErrorMessage;
import java.util.Arrays;

public enum CrewStatus {
    GOOD("정상", 0, 1),
    WARN("경고", 2, 2), // 2만
    INTERVIEW("면담", 3, 5), // 3부터 5까지
    WEEDING("제적", 6, Integer.MAX_VALUE); // 6 이상


    private final String crewStatusKor;
    private final Integer absenceCountFrom;
    private final Integer absenceCountTo;

    CrewStatus(String crewStatusKor, Integer absenceCountFrom, Integer absenceCountTo) {
        this.crewStatusKor = crewStatusKor;
        this.absenceCountFrom = absenceCountFrom;
        this.absenceCountTo = absenceCountTo;
    }

    public static CrewStatus findByAbsenceCount(Integer count) {
        return Arrays.stream(CrewStatus.values())
                .filter(status -> status.contains(count))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }

    public static CrewStatus judgeCrewStatus(int calculatedAbsenceCount) {
        if (0 <= calculatedAbsenceCount && calculatedAbsenceCount <= 1) {
            return GOOD;
        }
        if (calculatedAbsenceCount == 2) {
            return WARN;
        }
        if (3 <= calculatedAbsenceCount && calculatedAbsenceCount <= 5) {
            return WEEDING;
        }
        if (calculatedAbsenceCount > 5) {
            return WEEDING;
        }
        throw new IllegalArgumentException(ErrorMessage.PREFIX + "계산된 결석횟수는 반드시 0이상의 정수여야 합니다.");
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

    public String getCrewStatusKor() {
        return crewStatusKor;
    }
}
