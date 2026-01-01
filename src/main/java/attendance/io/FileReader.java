package attendance.io;

import static attendance.domain.CrewStatus.GOOD;

import attendance.converter.StringToLocalDateTimeConverter;
import attendance.domain.Crew;
import attendance.domain.CrewCatalog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static final String CSV_FILE_NAME = "attendances.csv";
    private static final String DELIMITER = ",";

    public CrewCatalog makeCrews() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CSV_FILE_NAME);
        CrewCatalog crewCatalog = new CrewCatalog(new ArrayList<>());
        if (inputStream == null) {
            throw new IllegalArgumentException(CSV_FILE_NAME + " 파일이 없습니다.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines()
                    .skip(1) // 헤더 스킵
                    .filter(line -> !line.isBlank()) // 빈줄 건너뛰기
                    .map(line -> line.split(DELIMITER))
                    .forEach(row -> {
                        String readName = row[0].trim();
                        if (!crewCatalog.isCrewExists(readName)) {
                            crewCatalog.addCrew(new Crew(readName, GOOD));
                        }
                    });
            return crewCatalog;
        } catch (IOException e) {
            throw new IllegalArgumentException(CSV_FILE_NAME + "파일을 읽는 과정에서 오류가 발생했습니다.");
        }
    }

    public List<LocalDateTime> makeAttendances() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CSV_FILE_NAME);
        StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(DELIMITER))
                    .map(line -> converter.convertToMinute(line[1].trim()))
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException(CSV_FILE_NAME + "파일을 읽는 과정에서 오류가 발생했습니다.");
        }
    }
}

