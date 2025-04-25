package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        addCheck("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", (n) -> n == null || n > 0);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        Predicate<Integer> check = number -> number != null && number >= begin && number <= end;
        addCheck("range", check);
        return this;

    }

    protected void addCheck(String checkName, Predicate<Integer> checkFunction) {
        super.addCheck(checkName, checkFunction);
    }
}
