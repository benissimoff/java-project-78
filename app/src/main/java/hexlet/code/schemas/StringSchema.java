package hexlet.code.schemas;

import java.util.function.Function;

public class StringSchema extends BaseSchema<String> {
//    public StringSchema required() {
//        Function<String, Boolean> check = text -> text != null && !text.isEmpty();
//
//        super.addCheck(check);
//
//        return this;
//    }

    public StringSchema minLength(int minLength) {
        Function<String, Boolean> check = text -> text != null && text.length() >= minLength;

        super.addCheck(check);

        return this;
    }

    public StringSchema contains(String containText) {
        Function<String, Boolean> check = text -> text != null && text.contains(containText);

        super.addCheck(check);

        return this;
    };

}
