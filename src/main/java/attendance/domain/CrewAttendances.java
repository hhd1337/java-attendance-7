package attendance.domain;

import java.util.List;

public class CrewAttendances {
    private List<CrewAttendance> crewAttendances;

    public CrewAttendances(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public CrewAttendance findCrewAttendanceByName(String name) {
        return crewAttendances.stream()
                .filter(crewAttendance -> crewAttendance.getCrewName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 크루의 출석을 조회할 수 없습니다."));
    }

    public List<CrewAttendance> getCrewAttendances() {
        return this.crewAttendances;
    }

}
