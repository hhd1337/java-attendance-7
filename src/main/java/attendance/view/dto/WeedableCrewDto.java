package attendance.view.dto;

public class WeedableCrewDto {
    private String crewName;
    private int absenceCount;
    private int lateCount;
    private String crewStatus;

    public WeedableCrewDto(String crewName, int absenceCount, int lateCount, String crewStatus) {
        this.crewName = crewName;
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
        this.crewStatus = crewStatus;
    }

    public String getName() {
        return this.crewName;
    }

    public int getAbsenceCount() {
        return this.absenceCount;
    }

    public int getLateCount() {
        return this.lateCount;
    }

    public String getCrewStatus() {
        return this.crewStatus;
    }
}
