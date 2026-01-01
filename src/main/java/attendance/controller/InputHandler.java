package attendance.controller;

import attendance.converter.StringToMenuConverter;
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
        StringToMenuConverter conveter = new StringToMenuConverter();
        return inputTemplate.execute(
                inputView::inputMenu,
                value -> {
                    value = value.trim();
                    return conveter.convert(value);
                }
        );
    }
}