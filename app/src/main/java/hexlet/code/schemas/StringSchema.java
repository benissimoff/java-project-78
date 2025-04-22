package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StringSchema extends Schema {
    private final List<Function<String, Boolean>> checks = new ArrayList<Function<String, Boolean>>();
    public boolean isValid(String exampleText) {
        for (var check : checks) {
            System.out.println("IN FOR " + check );

            var result = !check.apply(exampleText);
            System.out.println("result " + result);
            if (result) {
                return false;
            };
        }

        return true;
    }

    public StringSchema required() {
        Function<String, Boolean> check = text -> text != null && !text.isEmpty();

        checks.add(check);

        return this;
    }

    public StringSchema minLength(int minLength) {
        Function<String, Boolean> check = text -> text != null && text.length() >= minLength;

        checks.add(check);

        return this;
    }

    public StringSchema contains(String containText) {
        Function<String, Boolean> check = text -> text != null && text.contains(containText);

        checks.add(check);

        return this;
    };
}
