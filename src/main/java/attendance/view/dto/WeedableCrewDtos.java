package attendance.view.dto;

import java.util.List;

public class WeedableCrewDtos {
    private List<WeedableCrewDto> weedableCrewDtos;

    public WeedableCrewDtos(List<WeedableCrewDto> weedableCrewDtos) {
        this.weedableCrewDtos = weedableCrewDtos;
    }

    public List<WeedableCrewDto> getWeedableCrewDtos() {
        return this.weedableCrewDtos;
    }
}
