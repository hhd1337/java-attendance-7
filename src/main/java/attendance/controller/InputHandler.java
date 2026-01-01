package attendance.controller;

import attendance.converter.StringToMenuConverter;
import attendance.domain.Crew;
import attendance.domain.CrewCatalog;
import attendance.domain.Menu;
import attendance.view.InputView;

public class InputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate inputTemplate;

    public InputHandler(InputView inputView, IteratorInputTemplate inputTemplate) {
        this.inputView = inputView;
        this.inputTemplate = inputTemplate;
    }

    public Menu inputMenu() {
        StringToMenuConverter converter = new StringToMenuConverter();
        return inputTemplate.execute(
                inputView::inputMenu,
                value -> {
                    value = value.trim();
                    return converter.convert(value);
                }
        );
    }

    public Crew inputNickName(CrewCatalog crewCatalog) {
        return inputTemplate.execute(
                inputView::inputNickName,
                nickName -> {
                    nickName = nickName.trim();
                    return crewCatalog.findCrewByName(nickName);
                }
        );
    }
}