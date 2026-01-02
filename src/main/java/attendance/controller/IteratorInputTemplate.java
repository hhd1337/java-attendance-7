package attendance.controller;

import attendance.converter.Converter;
import attendance.util.ErrorMessage;
import attendance.view.OutputView;
import java.util.function.Supplier;

public class IteratorInputTemplate {

    private final OutputView outputView;

    public IteratorInputTemplate(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T execute(Supplier<String> inputSupplier, Converter<String, T> converter) {
        while (true) {
            try {
                String input = inputSupplier.get();
                return converter.convert(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
                throw new IllegalArgumentException(ErrorMessage.PREFIX + e.getMessage());
            }
        }
    }
}
