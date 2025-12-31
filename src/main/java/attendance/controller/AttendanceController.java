package attendance.controller;

import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;

public class AttendanceController {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public AttendanceController(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public void process() {
        outputView.printHelloAndMenu(DateTimes.now());
    }
}
