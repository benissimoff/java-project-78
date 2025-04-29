package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        Predicate<String> check = text -> text != null && !text.isEmpty();

        super.addCheck("required", check);

        return this;
    }

    public StringSchema minLength(int minSize) {
        Predicate<String> check = text -> text != null && text.length() >= minSize;

        addCheck("minLength", check);

        return this;
    }

    public StringSchema contains(String containText) {
        Predicate<String> check = text -> text != null && text.contains(containText);

        addCheck("contains", check);

        return this;
    };

    protected void addCheck(String checkName, Predicate<String> checkFunction) {
        super.addCheck(checkName, checkFunction);
    }

}
