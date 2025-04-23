package hexlet.code.schemas;

import java.util.function.Function;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        Function<String, Boolean> check = text -> text != null && !text.isEmpty();

        super.addCheck("required", check);

        return this;
    }

    public StringSchema minLength(int minSize) {
        Function<String, Boolean> check = text -> text != null && text.length() >= minSize;

        super.addCheck("minLength", check);

        return this;
    }

    public StringSchema contains(String containText) {
        Function<String, Boolean> check = text -> text != null && text.contains(containText);

        super.addCheck("contains", check);

        return this;
    };

}
