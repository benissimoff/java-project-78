package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseSchema<T> {
    private final Map<String, Function<T, Boolean>> validations = new HashMap<>();

    public final boolean isValid(T param) {
        for (var check : validations.values()) {
            var result = !check.apply(param);
            if (result) {
                return false;
            }
        }

        return true;
    }

    protected final void addCheck(String checkName, Function<T, Boolean> checkFunction) {
        validations.put(checkName, checkFunction);
    }
}
