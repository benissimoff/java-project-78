package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class TestApp {
    private static Validator validator = new Validator();
    private static final int MIN_LENGTH = 3;
    private static final int MAGIC_NUMBER_3 = 3;
    private static final int MAGIC_NUMBER_5 = 5;
    private static final int MAGIC_NUMBER_10 = 10;
    private static final int MAGIC_NUMBER_99 = 99;

    @ParameterizedTest
    @ValueSource(strings = {"", "abc", "long text"})
    public void checkSimpleStringValidator(String param) {
        StringSchema stringSimpleValidator = validator.string();
        boolean actual = stringSimpleValidator.isValid(param);
        assertTrue(actual);
    }

    @ParameterizedTest
    @NullSource
    public void checkStringRequiredNull(String param) {
        StringSchema simpleValidator = validator.string();
        StringSchema requireValidator = validator.string().required();

        assertTrue(simpleValidator.isValid(param));
        assertFalse(requireValidator.isValid(param));
    }

    @Test
    public void checkStringRequired() {
        StringSchema stringValidator = validator.string().required();
        String testText;
        boolean actual;

        testText = "";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "abc";
        actual = stringValidator.isValid(testText);
        assertTrue(actual);
    }

    @Test
    public void checkStringLength() {

        StringSchema stringValidator = validator.string().minLength(MAGIC_NUMBER_5);

        String testText;
        boolean actual;

        testText = null;
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "12";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "123";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "123456";
        actual = stringValidator.isValid(testText);
        assertTrue(actual);

        stringValidator.contains("345");

        testText = "123456";
        actual = stringValidator.isValid(testText);
        assertTrue(actual);

        testText = "87603424";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        var longValidator = validator.string().required().minLength(2).contains("bca");
        testText = "abcabc";
        actual = longValidator.isValid(testText);
        assertTrue(actual);

    }

    @Test
    public void checkStringContain() {
        StringSchema stringValidator = validator.string().contains("345");
        String testText;
        boolean actual;

        testText = null;
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

        testText = "123456";
        actual = stringValidator.isValid(testText);
        assertTrue(actual);

        testText = "87603424";
        actual = stringValidator.isValid(testText);
        assertFalse(actual);

    }

    @Test
    public void checkStringLongValidator() {
        StringSchema longValidator = validator.string().required().minLength(MIN_LENGTH).contains("bca");
        String testText = "abcabc";
        boolean actual = longValidator.isValid(testText);
        assertTrue(actual);

        StringSchema schema1 = validator.string();
        actual = schema1.minLength(MAGIC_NUMBER_10).minLength(MAGIC_NUMBER_5).isValid("Hexlet"); // true
        assertTrue(actual);
    }

    @Test
    public void testNumber() {
        var numberValidator = validator.number();
        int testNumber;
        boolean actual;

        actual = numberValidator.isValid(null);
        assertTrue(actual); // true

        testNumber = MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertTrue(actual);

        // с этим тестом я не согласен
        // с точки зрения математики "положительное число" это, во-первых, число
        // ну, то есть как минимум не "null"/"пустота"
        actual = numberValidator.positive().isValid(null);
        assertTrue(actual);

        numberValidator.required().positive();

        actual = numberValidator.isValid(null);
        assertFalse(actual);

        testNumber = 0;
        actual = numberValidator.isValid(testNumber);
        assertFalse(actual);

        testNumber = -MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertFalse(actual);

        testNumber = MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertTrue(actual);
    }

    @Test
    public void checkNumberRange() {
        NumberSchema numberValidator = validator.number();
        int testNumber;
        boolean actual;

        var start = MAGIC_NUMBER_5;
        var finish = MAGIC_NUMBER_10;
        numberValidator.range(start, finish);

        testNumber = MAGIC_NUMBER_5;
        actual = numberValidator.isValid(testNumber);
        assertTrue(actual);

        testNumber = MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertTrue(actual);

        testNumber = MAGIC_NUMBER_3;
        actual = numberValidator.isValid(testNumber);
        assertFalse(actual);

        testNumber = MAGIC_NUMBER_99;
        actual = numberValidator.isValid(testNumber);
        assertFalse(actual);

        actual = numberValidator.isValid(null);
        assertFalse(actual);

        start = 0;
        finish = MAGIC_NUMBER_99;
        numberValidator.required().positive().range(start, finish);
        testNumber = MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertTrue(actual);

        testNumber = MAGIC_NUMBER_99 * MAGIC_NUMBER_10;
        actual = numberValidator.isValid(testNumber);
        assertFalse(actual);

    }

    @Test
    public void testMap() {
        var schema = validator.map();
        boolean actual;
        Map<String, String> testMap;

        actual = schema.isValid(null);
        assertTrue(actual);

        schema.required();

        actual = schema.isValid(null);
        assertFalse(actual);

        testMap = new HashMap<>();
        actual = schema.isValid(testMap);
        assertTrue(actual);

        testMap = new HashMap<>();
        testMap.put("key1", "value1");
        actual = schema.isValid(testMap);
        assertTrue(actual);

        schema.sizeof(2);

        actual = schema.isValid(testMap);
        assertFalse(actual);

        testMap.put("key2", "value2");
        actual = schema.isValid(testMap);
        assertTrue(actual);

    }

    @Test
    public void testMapShape() {
        var schema = validator.map();
        boolean actual;
        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        actual = schema.isValid(human1);
        assertTrue(actual);

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        actual = schema.isValid(human2);
        assertFalse(actual);

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        actual = schema.isValid(human3);
        assertFalse(actual);
    }
}
