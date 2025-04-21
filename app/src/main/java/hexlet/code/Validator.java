package hexlet.code;

import hexlet.code.schemas.Schema;
import hexlet.code.schemas.StringSchema;

public class Validator {
    private Schema schema;
    public Schema string() {
        schema = new StringSchema();
        return this.schema;
    }
}
