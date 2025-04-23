package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestApp {
    private static final int MIN_LENGTH = 3;
    private static final int MAGIC_NUMBER_3 = 3;
    private static final int MAGIC_NUMBER_5 = 5;
    private static final int MAGIC_NUMBER_10 = 10;
    private static final int MAGIC_NUMBER_99 = 99;


    @Test
    public void checkString() {
        Validator validator = new Validator();
        StringSchema stringValidator;
        stringValidator = validator.string();

        String testEmptyText = "";
        boolean actual;

        actual = stringValidator.isValid(testEmptyText);
        assertTrue(actual);

        stringValidator.required();
        actual = stringValidator.isValid(null);
        assertFalse(actual);

        actual = stringValidator.isValid("test");
        assertTrue(actual);


        stringValidator.minLength(MIN_LENGTH);
        String testTextFail = "12";
        actual = stringValidator.isValid(testTextFail);
        assertFalse(actual);

        String testTextTrue = "123";
        actual = stringValidator.isValid(testTextTrue);
        assertTrue(actual);

        testTextTrue = "123456";
        actual = stringValidator.isValid(testTextTrue);
        assertTrue(actual);

        stringValidator.contains("345");

        testTextTrue = "123456";
        actual = stringValidator.isValid(testTextTrue);
        assertTrue(actual);

        testTextFail = "87603424";
        actual = stringValidator.isValid(testTextFail);
        assertFalse(actual);

        var longValidator = validator.string().required().minLength(2).contains("bca");
        testTextTrue = "abcabc";
        actual = longValidator.isValid(testTextTrue);
        assertTrue(actual);

    }

    @Test
    public void testNumber() {
        var v = new Validator();

        var schema = v.number();
        boolean actual;

        var testNumber = MAGIC_NUMBER_5;
        actual = schema.isValid(testNumber);
        assertTrue(actual); // true

// Пока не вызван метод required(), null считается валидным
        actual = schema.isValid(null);
        assertTrue(actual); // true

        actual = schema.positive().isValid(null); // true
        assertTrue(actual);

        schema.required();

        actual = schema.isValid(null); // false
        assertFalse(actual);

        testNumber = MAGIC_NUMBER_10;
        actual = schema.isValid(testNumber); // true
        assertTrue(actual);

        // Потому что ранее мы вызвали метод positive()
        testNumber = -MAGIC_NUMBER_10;
        actual = schema.isValid(testNumber); // false
        assertFalse(actual);

        //  Ноль — не положительное число
        testNumber = 0;
        actual = schema.isValid(testNumber); // false
        assertFalse(actual);

        var start = MAGIC_NUMBER_5;
        var finish = MAGIC_NUMBER_10;
        schema.range(start, finish);

        testNumber = MAGIC_NUMBER_5;
        actual = schema.isValid(testNumber); // true
        assertTrue(actual);

        testNumber = MAGIC_NUMBER_10;
        actual = schema.isValid(testNumber); // true
        assertTrue(actual);

        testNumber = MAGIC_NUMBER_3;
        actual = schema.isValid(testNumber); // false
        assertFalse(actual);

        testNumber = MAGIC_NUMBER_99;
        actual = schema.isValid(testNumber); // false
        assertFalse(actual);

        start = 0;
        finish = MAGIC_NUMBER_99;
        var longValidator = v.number().required().positive().range(start, finish);
        var number = MAGIC_NUMBER_10;
        actual = longValidator.isValid(number);
        assertTrue(actual);

        number = MAGIC_NUMBER_99 * MAGIC_NUMBER_10;
        actual = longValidator.isValid(number);
        assertFalse(actual);

    }

    @Test
    public void testMap() {
        var v = new Validator();
        var schema = v.map();
        boolean actual;

        actual = schema.isValid(null); // true
        assertTrue(actual);

        schema.required();

        actual = schema.isValid(null); // false
        assertFalse(actual);

        actual = schema.isValid(new HashMap<>()); // true
        assertTrue(actual);

        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        actual = schema.isValid(data); // true
        assertTrue(actual);


        schema.sizeof(2);

        actual = schema.isValid(data);  // false
        assertFalse(actual);

        data.put("key2", "value2");
        actual = schema.isValid(data); // true
        assertTrue(actual);

    }

    @Test
    public void testMapShape() {
        var v = new Validator();

        var schema = v.map();

        boolean actual;
// shape позволяет описывать валидацию для значений каждого ключа объекта Map
// Создаем набор схем для проверки каждого ключа проверяемого объекта
// Для значения каждого ключа - своя схема
        Map<String, BaseSchema<String>> schemas = new HashMap<>();

// Определяем схемы валидации для значений свойств "firstName" и "lastName"
// Имя должно быть строкой, обязательно для заполнения
        schemas.put("firstName", v.string().required());
// Фамилия обязательна для заполнения и должна содержать не менее 2 символов
        schemas.put("lastName", v.string().required().minLength(2));

// Настраиваем схему `MapSchema`
// Передаем созданный набор схем в метод shape()
        schema.shape(schemas);

// Проверяем объекты
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        actual = schema.isValid(human1); // true
        assertTrue(actual);

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        actual = schema.isValid(human2); // false
        assertFalse(actual);

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        actual = schema.isValid(human3); // false
        assertFalse(actual);
    }
}
