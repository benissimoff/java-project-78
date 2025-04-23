package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseSchema<T> {
    private final List<Function<T, Boolean>> checks = new ArrayList<>();
    public final boolean isValid(T param) {
        for (var check : checks) {
            var result = !check.apply(param);
            if (result) {
                return false;
            }
        }

        return true;
    }

    protected final void addCheck(Function<T, Boolean> checkFunction) {
        checks.add(checkFunction);
    }
}
