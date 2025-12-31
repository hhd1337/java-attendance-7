package attendance.domain;

public class Crew {
    private String name;
    private CrewStatus status;

    public Crew(String name, CrewStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public CrewStatus getStatus() {
        return this.status;
    }

}
