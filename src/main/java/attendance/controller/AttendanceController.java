package attendance.controller;

import static attendance.domain.AttendanceState.ABSENCE;
import static attendance.domain.AttendanceState.LATE;
import static attendance.domain.AttendanceState.SUCCESS;

import attendance.domain.AttendanceState;
import attendance.domain.AttendanceTimeRule;
import attendance.domain.Crew;
import attendance.domain.CrewAttendance;
import attendance.domain.CrewAttendances;
import attendance.domain.CrewCatalog;
import attendance.domain.CrewStatus;
import attendance.domain.Menu;
import attendance.io.FileReader;
import attendance.util.ErrorMessage;
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
            run(menu, crewCatalog, crewAttendances, DateTimes.now());
        } while (menu != Menu.QUIT);
    }

    private void run(Menu menu, CrewCatalog crewCatalog, CrewAttendances crewAttendances, LocalDateTime currDateTime) {

        if (menu == Menu.INSERT_ATTENDANCE) {
            runInsertAttendance(crewCatalog, crewAttendances);
        }
        if (menu == Menu.UPDATE_ATTENDANCE) {
            runUpdateAttendance(crewCatalog, crewAttendances);
        }
        if (menu == Menu.CHECK_MONTHLY) {
            runCheckAttendanceMonthly(crewCatalog, crewAttendances, currDateTime);
        }
        if (menu == Menu.CHECK_WEEDERS) {
            // runCheckWeeders();
        }
    }

    private void runCheckAttendanceMonthly(CrewCatalog crewCatalog, CrewAttendances crewAttendances,
                                           LocalDateTime currDateTime) {
        outputView.printNickNameInputPrompt();
        Crew crew = inputHandler.inputNickName(crewCatalog);
        CrewAttendance crewAttendance = crewAttendances.findCrewAttendanceByName(crew.getName());
        // 여기서 히스토리를 돌면서 프린트 하면 안될거같음.
        // 날짜 순서대로 Iteration (이번달 1일부터 오늘 날짜 이전날까지) !!!!!!!! 완.
        // 해당 날짜 crewAttendanceHistory에서 찾아서 print 하면서, (!HOLIDAY && !WEEKEND 일때만 제외하고 print)
        crewAttendance.initCountsToZero(); // 0으로 counts 다 초기화
        for (LocalDate indexDate = currDateTime.toLocalDate().withDayOfMonth(1);
             indexDate.isBefore(currDateTime.toLocalDate());
             indexDate = indexDate.plusDays(1)) {

            // 당일 출석기록 찾음
            LocalDateTime indexDateTime = crewAttendance.findDateTimeByDateOrNull(indexDate);

            // HOLIDAY || WEEKEND 일때는 넘어감.
            if (AttendanceTimeRule.from(indexDate) == AttendanceTimeRule.HOLIDAY
                    || AttendanceTimeRule.from(indexDate) == AttendanceTimeRule.WEEKEND) {
                continue;
            }
            // 이번날짜(indexDateTime)가 출석부에 없으면 "--:--" 형식으로 출력
            // 해당 크루의 ActualAbsenceCount 증가
            if (indexDateTime == null) {
                outputView.printAttendResultPromptForNoHistory(indexDate, ABSENCE.getKorState());
                crewAttendance.encreaseActualAbsenceCount();
            }
            // 이번날짜(indexDateTime)가 출석부에 있으면 출력
            if (indexDateTime != null) {
                AttendanceState attendanceState = AttendanceTimeRule.from(indexDate)
                        .judgeAttendance(indexDateTime.toLocalTime());
                String attendanceStateKor = attendanceState.getKorState();
                outputView.printAttendResultPrompt(indexDateTime, attendanceStateKor);
                if (attendanceState == SUCCESS) {
                    crewAttendance.encreaseSuccessCount();
                }
                if (attendanceState == LATE) {
                    crewAttendance.encreaseLateCount();
                }
                if (attendanceState == ABSENCE) {
                    crewAttendance.encreaseActualAbsenceCount();
                }
            }
        }

        int success = crewAttendance.getSuccessCount();
        int late = crewAttendance.getLateCount();
        int absence = crewAttendance.getActualAbsenceCount();
        int calculatedAbsence = crewAttendance.getCalculatedAbsenceCount();

        CrewStatus crewStatus = CrewStatus.judgeCrewStatus(calculatedAbsence);

        // 출석, 지각, 결석, CrewStatus 출력
        outputView.printAttendanceStateOfMonth(success, late, absence, crewStatus.getCrewStatusKor());
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
        AttendanceState attendanceState = AttendanceTimeRule.from(arrivedTime.toLocalDate())
                .judgeAttendance(arrivedTime.toLocalTime());
        // CrewCatalog 에 학생 출석정보 추가하기
        CrewAttendance crewAttendance = crewAttendances.findCrewAttendanceByName(crew.getName());
        crewAttendance.addAttendance(arrivedTime);
        // 출력하기
        outputView.printAttendResultPrompt(arrivedTime, attendanceState.getKorState());
    }

    private void runUpdateAttendance(CrewCatalog crewCatalog, CrewAttendances crewAttendances) {
        outputView.printNickNameInputForUpdatePrompt();
        Crew crew = inputHandler.inputNickName(crewCatalog);
        CrewAttendance crewAttendance = crewAttendances.findCrewAttendanceByName(crew.getName());

        retryUntilValid(() -> {
            outputView.printDateForMonthInputPrompt();
            LocalDate updateDate = inputHandler.inputDateForMonth(DateTimes.now().toLocalDate());

            outputView.printUpdateTimeInputPrompt();

            // oldDateTime, oldAttendStatus 구함
            LocalDateTime oldDateTime = crewAttendance.findDateTimeByDateOrNull(updateDate);
            if (oldDateTime == null) {
                throw new IllegalArgumentException(ErrorMessage.PREFIX + "수정하려는 날짜에 해당 크루의 출석 기록이 없습니다.");
            }
            AttendanceState oldAttendanceState = AttendanceTimeRule.from(oldDateTime.toLocalDate())
                    .judgeAttendance(oldDateTime.toLocalTime());

            // newDateTime 구함
            LocalDateTime newDateTime = inputHandler.inputUpdateTime(updateDate);

            // crewAttendance에 수정
            crewAttendance.updateAttendance(newDateTime);

            // newAttendStatus 구함
            AttendanceState newAttendanceState = AttendanceTimeRule.from(newDateTime.toLocalDate())
                    .judgeAttendance(newDateTime.toLocalTime());

            // 포맷에 맞게 출력
            outputView.printUpdateSuccessPrompt(oldDateTime, oldAttendanceState.getKorState(), newDateTime,
                    newAttendanceState.getKorState());
        });
    }

    private void retryUntilValid(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.PREFIX + e.getMessage());
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }
}
