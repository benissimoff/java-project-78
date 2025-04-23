package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseSchema<T> {
    private final List<Function<T, Boolean>> checks = new ArrayList<>();
    public boolean isValid(T param) {
        for (var check : checks) {
            System.out.println("IN FOR " + check );

            var result = !check.apply(param);
            System.out.println("result " + result);
            if (result) {
                return false;
            };
        }

        return true;
    }

    protected void addCheck(Function<T, Boolean> checkFunction) {
        checks.add(checkFunction);
    }

    public BaseSchema<T> required() {
        Function<T, Boolean> check = Objects::nonNull;
        this.addCheck(check);
        return this;
    }
}
