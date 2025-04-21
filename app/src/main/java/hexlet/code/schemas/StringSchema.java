package hexlet.code.schemas;

import java.util.Map;

public class StringSchema implements Schema {

    private Map<String, String> validators;

    public boolean isValid(String exampleText) {
        return false;
    }

    public Schema required() {
        return this;
    }

    public Schema minLength(int minLength) {
        return this;
    }

    public Schema contains(String containText) {
        return this;
    };
}
