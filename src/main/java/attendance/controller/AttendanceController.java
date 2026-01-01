package attendance.controller;

import attendance.domain.Crew;
import attendance.domain.CrewCatalog;
import attendance.domain.Menu;
import attendance.io.FileReader;
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
        FileReader fileReader = new FileReader();
        CrewCatalog crewCatalog = fileReader.makeCrews();

        Menu menu;
        do {
            outputView.printHelloAndMenu(DateTimes.now());
            menu = inputHandler.inputMenu();
            run(menu, crewCatalog);
        } while (menu != Menu.QUIT);
    }

    private void run(Menu menu, CrewCatalog crewCatalog) {
        if (menu == Menu.INSERT_ATTENDANCE) {
            runInsertAttendance(crewCatalog);
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

    private void runInsertAttendance(CrewCatalog crewCatalog) {
        outputView.printNickNameInputPrompt();
        Crew crew = inputHandler.inputNickName(crewCatalog);

    }
}
