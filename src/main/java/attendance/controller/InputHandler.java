package attendance.controller;

import attendance.view.InputView;

public class InputHandler {

    private final InputView inputView;
    private final IteratorInputTemplate inputTemplate;

    public InputHandler(InputView inputView, IteratorInputTemplate iteratorInputTemplate) {
        this.inputView = inputView;
        this.inputTemplate = iteratorInputTemplate;
    }
}