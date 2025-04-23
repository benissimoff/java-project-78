package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Function;

public class NumberSchema extends BaseSchema<Integer> {
//    public NumberSchema required() {
//        Function<Integer, Boolean> check = Objects::nonNull;
//        super.addCheck(check);
//        return this;
//    }

    public NumberSchema positive() {
        Function<Integer, Boolean> check = (number) -> number == null || number > 0;
        super.addCheck(check);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        Function<Integer, Boolean> check = number -> number != null && number >= begin && number <= end;
        super.addCheck(check);
        return this;

    }
}
