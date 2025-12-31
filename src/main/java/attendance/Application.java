package attendance;

import attendance.controller.AttendanceController;

public class Application {
    public static void main(String[] args) {
        AttendanceConfig attendanceConfig = new AttendanceConfig();
        AttendanceController attendanceController = attendanceConfig.attendanceController();
        attendanceController.process();
    }
}
