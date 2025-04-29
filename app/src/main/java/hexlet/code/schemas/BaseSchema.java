package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final Map<String, Predicate<T>> validations = new HashMap<>();

    public final boolean isValid(T param) {
        return validations.values()
                .stream()
                .allMatch((check) -> check.test(param));
    }

    /**
     * Add validation check to list of validations.
     * @param checkName - name of validation
     * @param checkFunction - validation function
     */
    protected void addCheck(String checkName, Predicate<T> checkFunction) {
        validations.put(checkName, checkFunction);
    }
}
