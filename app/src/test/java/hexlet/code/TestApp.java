package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class TestApp {
    private static Validator validator = new Validator();
    private static final int MAGIC_NUMBER_0 = 0;
    private static final int MAGIC_NUMBER_3 = 3;
    private static final int MAGIC_NUMBER_5 = 5;
    private static final int MAGIC_NUMBER_10 = 10;
    private static final int MAGIC_NUMBER_99 = 99;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "long text", "Hexlet", "Very very long text"})
    public void checkSimpleStringValidator(String param) {
        StringSchema stringSimpleValidator = validator.string();
        boolean actual = stringSimpleValidator.isValid(param);
        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void checkStringRequiredNullAndEmpty(String param) {
        StringSchema stringValidator = validator.string().required();
        boolean actual = stringValidator.isValid(param);
        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hexlet", "long text", "Very very long text"})
    public void checkStringMinLengthGood(String param) {
        StringSchema stringValidator = validator.string().minLength(MAGIC_NUMBER_5);
        boolean actual = stringValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "abcd"})
    public void checkStringMinLengthFail(String param) {
        StringSchema stringValidator = validator.string().minLength(MAGIC_NUMBER_5);
        boolean actual = stringValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"a", "abcd", "Hexlet", "long text", "Very very long text"})
    public void checkStringMinLength0(String param) {
        StringSchema stringValidator = validator.string().minLength(0);
        boolean actual = stringValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcd", "abcdfabcd"})
    public void checkStringContainsGood(String param) {
        StringSchema stringValidator = validator.string().contains("abc");
        boolean actual = stringValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abbc", "Hexlet", "long text", "Very very long text"})
    public void checkStringContainsFail(String param) {
        StringSchema stringValidator = validator.string().contains("abc");
        boolean actual = stringValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aabcc", "abbcdddfffabc", "ffffabcdfabcd"})
    public void checkStringChainBuildGood(String param) {
        var stringValidator = validator.string()
                .required()
                .minLength(MAGIC_NUMBER_10)
                .minLength(MAGIC_NUMBER_5)
                .contains("abc");
        boolean actual = stringValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"abc", "aabbcc", "Long test text"})
    public void checkStringChainBuildFail(String param) {
        var stringValidator = validator.string()
                .required()
                .minLength(MAGIC_NUMBER_10)
                .minLength(MAGIC_NUMBER_5)
                .contains("abc");
        boolean actual = stringValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {MAGIC_NUMBER_0, -MAGIC_NUMBER_3, -MAGIC_NUMBER_10, -MAGIC_NUMBER_99
    })
    public void testNumberAny(Integer param) {
        var numberValidator = validator.number();
        boolean actual = numberValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {
        MAGIC_NUMBER_0,
        -MAGIC_NUMBER_5,
        MAGIC_NUMBER_10,
        MAGIC_NUMBER_99
    })
    public void testNumberRequiredGood(Integer param) {
        var numberValidator = validator.number().required();
        boolean actual = numberValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullSource
    public void testNumberRequiredFail(Integer param) {
        var numberValidator = validator.number().required();
        boolean actual = numberValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    /*
    с этим тестом я не согласен
    с точки зрения математики "положительное число" это, во-первых, число
    ну, то есть не "null"/"пустота"
    */
    @NullSource
    @ValueSource(ints = {
        MAGIC_NUMBER_5,
        MAGIC_NUMBER_10,
        MAGIC_NUMBER_99
    })
    public void testNumberPositiveGood(Integer param) {
        var numberValidator = validator.number().positive();
        boolean actual = numberValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {
        MAGIC_NUMBER_0,
        -MAGIC_NUMBER_5,
        -MAGIC_NUMBER_10,
        -MAGIC_NUMBER_99
    })
    public void testNumberPositiveFail(Integer param) {
        var numberValidator = validator.number().positive();
        boolean actual = numberValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {MAGIC_NUMBER_3, MAGIC_NUMBER_5, MAGIC_NUMBER_10,
    })
    public void testNumberRangeGood(Integer param) {
        var numberValidator = validator.number().range(MAGIC_NUMBER_3, MAGIC_NUMBER_10);
        boolean actual = numberValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-MAGIC_NUMBER_3, -MAGIC_NUMBER_5, -MAGIC_NUMBER_10, MAGIC_NUMBER_99
    })
    public void testNumberRangeFail(Integer param) {
        var numberValidator = validator.number().range(MAGIC_NUMBER_3, MAGIC_NUMBER_10);
        boolean actual = numberValidator.isValid(param);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {MAGIC_NUMBER_3, MAGIC_NUMBER_5, MAGIC_NUMBER_10,
    })
    public void testNumberChainGood(Integer param) {
        var numberValidator = validator.number()
                .required()
                .positive()
                .range(MAGIC_NUMBER_10, MAGIC_NUMBER_99)
                .range(MAGIC_NUMBER_3, MAGIC_NUMBER_10);
        boolean actual = numberValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {
        -MAGIC_NUMBER_3,
        -MAGIC_NUMBER_5,
        -MAGIC_NUMBER_10,
        MAGIC_NUMBER_99
    })
    public void testNumberChainFail(Integer param) {
        var numberValidator = validator.number()
                .required()
                .positive()
                .range(MAGIC_NUMBER_10, MAGIC_NUMBER_99)
                .range(MAGIC_NUMBER_3, MAGIC_NUMBER_10);
        boolean actual = numberValidator.isValid(param);

        assertFalse(actual);
    }

    private static Stream<HashMap<String, String>> streamOfMaps() {
        var testMap = new HashMap<String, String>();
        testMap.put("key1", "value1");
        var testMap2 = new HashMap<>(testMap);
        testMap2.put("key2", "value2");

        var result = Stream.of(testMap, testMap2);

        return result;
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("streamOfMaps")
    public void testMapAny(Map<Object, Object> param) {
        var mapValidator = validator.map();
        boolean actual = mapValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @EmptySource
    @MethodSource("streamOfMaps")
    public void testMapRequiredGood(Map<Object, Object> param) {
        var mapValidator = validator.map().required();
        boolean actual = mapValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullSource
    public void testMapRequiredFail(Map<Object, Object> param) {
        var mapValidator = validator.map().required();
        boolean actual = mapValidator.isValid(param);

        assertFalse(actual);
    }

    private static Stream<HashMap<String, String>> streamOfMapsSize3() {
        var testMap = new HashMap<String, String>();
        testMap.put("key1", "value1");
        testMap.put("key2", "value2");
        testMap.put("key3", "value3");

        var result = Stream.of(testMap);

        return result;
    }


    @ParameterizedTest
    @MethodSource("streamOfMapsSize3")
    public void testMapSizeGood(Map<Object, Object> param) {
        var mapValidator = validator.map().sizeof(MAGIC_NUMBER_3);
        boolean actual = mapValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("streamOfMaps")
    public void testMapSizeFail(Map<Object, Object> param) {
        var mapValidator = validator.map().sizeof(MAGIC_NUMBER_3);
        boolean actual = mapValidator.isValid(param);

        assertFalse(actual);
    }

    private static Map<String, BaseSchema<String>> buildSchema() {
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        return schemas;
    }

    private static Stream<Map<String, String>> streamOfMapGood() {
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");

        var result = Stream.of(human1);

        return result;
    }

    private static Stream<Map<String, String>> streamOfMapFail() {
        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");

        var result = Stream.of(human2, human3);

        return result;
    }

    @ParameterizedTest
    @MethodSource({"streamOfMapGood"})
    public void testMapSchemaGood(Map<Object, Object> param) {
        var schema = buildSchema();
        var mapValidator = validator.map().shape(schema);
        boolean actual = mapValidator.isValid(param);

        assertTrue(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource({"streamOfMapFail", "streamOfMapsSize3", "streamOfMaps"})
    public void testMapSchemaFalse(Map<Object, Object> param) {
        System.out.println("Param " + param);
        var schema = buildSchema();
        var mapValidator = validator.map().shape(schema);
        boolean actual = mapValidator.isValid(param);

        assertFalse(actual);
    }
}
