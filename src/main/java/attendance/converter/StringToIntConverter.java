package attendance.converter;

public class StringToIntConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 정수 형식으로 입력해주세요.");
        }
    }
}

