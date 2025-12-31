package attendance.domain;

import java.util.List;

public class CrewCatalog {
    private List<Crew> crews;

    public CrewCatalog(List<Crew> crews) {
        this.crews = crews;
    }

    public boolean isCrewExists(String name) {
        return crews.stream().anyMatch(crew -> crew.getName().equals(name));
    }

    public void addCrew(Crew crew) {
        this.crews.add(crew);
    }

    public List<Crew> getCrewList() {
        return this.crews;
    }
}
