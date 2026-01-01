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

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임을 가진 크루가 크루 목록에 없습니다."));
    }

    public void addCrew(Crew crew) {
        this.crews.add(crew);
    }

    public List<Crew> getCrewList() {
        return this.crews;
    }
}
