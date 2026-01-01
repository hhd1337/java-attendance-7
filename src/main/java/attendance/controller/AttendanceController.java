package attendance.controller;

import attendance.domain.Menu;
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
        Menu menu;
        do {
            outputView.printHelloAndMenu(DateTimes.now());
            menu = inputHandler.inputMenu();
            run(menu);
        } while (menu != Menu.QUIT);
    }

    private void run(Menu menu) {
        if (menu == Menu.INSERT_ATTENDANCE) {
            // runInsertAttendance();
        }
        if (menu == Menu.UPDATE_ATTENDANCE) {
            // runUpdateAttendance();
        }
        if (menu == Menu.CHECK_MONTHLY) {
            // runCheckAttendanceMonthly();
        }
        if (menu == Menu.CHECK_WEEDERS) {
            // runCheckWeeders();
        }
    }
}
