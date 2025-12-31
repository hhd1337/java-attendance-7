package attendance;

import attendance.controller.AttendanceController;
import attendance.controller.InputHandler;
import attendance.controller.IteratorInputTemplate;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceConfig {

    private InputView inputView;
    private OutputView outputView;
    private IteratorInputTemplate iteratorInputTemplate;
    private InputHandler inputHandler;
    private AttendanceController attendanceController;

    public InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    public OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

    public IteratorInputTemplate iteratorInputTemplate() {
        if (iteratorInputTemplate == null) {
            iteratorInputTemplate = new IteratorInputTemplate(outputView());
        }
        return iteratorInputTemplate;
    }

    public InputHandler iteratorInputHandler() {
        if (inputHandler == null) {
            inputHandler = new InputHandler(inputView(), iteratorInputTemplate());
        }
        return inputHandler;
    }

    public AttendanceController attendanceController() {
        if (attendanceController == null) {
            attendanceController = new AttendanceController(iteratorInputHandler(), outputView());
        }
        return attendanceController;
    }
}

