package attendance.controller;

import attendance.domain.AttendanceState;
import attendance.domain.AttendanceTimeRule;
import attendance.domain.Crew;
import attendance.domain.CrewAttendance;
import attendance.domain.CrewAttendances;
import attendance.domain.CrewCatalog;
import attendance.domain.Menu;
import attendance.io.FileReader;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    private final InputHandler inputHandler;
    private final OutputView outputView;

    public AttendanceController(InputHandler inputHandler, OutputView outputView) {
        this.inputHandler = inputHandler;
        this.outputView = outputView;
    }

    public void process() {
        FileReader fileReader = new FileReader();
        CrewCatalog crewCatalog = fileReader.makeCrews(); // crewCatalog 만들음
        CrewAttendances crewAttendances = loadCrewAttendances(crewCatalog, fileReader); // CrewAttendances 만들음

        Menu menu;
        do {
            outputView.printHelloAndMenu(DateTimes.now());
            menu = inputHandler.inputMenu();
            run(menu, crewCatalog, crewAttendances);
        } while (menu != Menu.QUIT);
    }

    private void run(Menu menu, CrewCatalog crewCatalog, CrewAttendances crewAttendances) {

        if (menu == Menu.INSERT_ATTENDANCE) {
            runInsertAttendance(crewCatalog, crewAttendances);
        }
        if (menu == Menu.UPDATE_ATTENDANCE) {
            runUpdateAttendance(crewCatalog, crewAttendances);
        }
        if (menu == Menu.CHECK_MONTHLY) {
            // runCheckAttendanceMonthly();
        }
        if (menu == Menu.CHECK_WEEDERS) {
            // runCheckWeeders();
        }
    }

    private void runUpdateAttendance(CrewCatalog crewCatalog, CrewAttendances crewAttendances) {
        outputView.printNickNameInputForUpdatePrompt();
        Crew crew = inputHandler.inputNickName(crewCatalog);

        outputView.printDateForMonthInputPrompt();
        LocalDate date = inputHandler.inputDateForMonth(DateTimes.now().toLocalDate());

        outputView.printUpdateTimeInputPrompt();
        LocalDateTime dateTime = inputHandler.inputUpdateTime(date);

        // crewAttendance에 수정
        CrewAttendance crewAttendance = crewAttendances.findCrewAttendanceByName(crew.getName());
        crewAttendance.updateAttendance(dateTime);
    }

    private CrewAttendances loadCrewAttendances(CrewCatalog crewCatalog, FileReader fileReader) {
        List<CrewAttendance> crewAttendanceList = new ArrayList<>();
        for (Crew crew : crewCatalog.getCrewList()) {
            CrewAttendance crewAttendance = fileReader.makeCrewAttendance(crew.getName());
            crewAttendanceList.add(crewAttendance);
        }
        return new CrewAttendances(crewAttendanceList);
    }

    private void runInsertAttendance(CrewCatalog crewCatalog, CrewAttendances crewAttendances) {
        outputView.printNickNameInputPrompt();
        Crew crew = inputHandler.inputNickName(crewCatalog);

        outputView.printArrivedTimeInputPrompt();
        LocalDateTime arrivedTime = inputHandler.inputArrivedTime(DateTimes.now().toLocalDate());

        // arrivedTime으로 출석인지 결석인지 확인
        AttendanceState attendanceState = AttendanceTimeRule.from(arrivedTime)
                .judgeAttendance(arrivedTime.toLocalTime());
        // CrewCatalog 에 학생 출석정보 추가하기
        CrewAttendance crewAttendance = crewAttendances.findCrewAttendanceByName(crew.getName());
        crewAttendance.addAttendance(arrivedTime);
        // 출력하기
        outputView.printAttendResultPrompt(arrivedTime, attendanceState.getKorState());
    }


}
