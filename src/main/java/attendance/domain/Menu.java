package attendance.domain;

import java.util.Arrays;

public enum Menu {
    INSERT_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    CHECK_MONTHLY("3"),
    CHECK_WEEDERS("4"),
    QUIT("Q");

    private final String symbol;

    Menu(String symbol) {
        this.symbol = symbol;
    }

    public static Menu findBySymbol(String symbol) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식을 입력하였습니다."));
    }

    public String getSymbol() {
        return this.symbol;
    }
}

