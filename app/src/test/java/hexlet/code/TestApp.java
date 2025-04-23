package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {
    @Test
    public void checkString() {

        System.out.println("!!! === checkString");

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

        stringValidator.minLength(3);
        String testTextFail = "12";
        actual = stringValidator.isValid(testTextFail);
        assertFalse(actual);

        String testTextTrue = "123";
        actual = stringValidator.isValid(testTextTrue);
        assertTrue(actual);
//        assertFalse(actual);

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

        actual = schema.isValid(5);
        assertTrue(actual); // true

// Пока не вызван метод required(), null считается валидным
        actual = schema.isValid(null);
        assertTrue(actual); // true

        actual = schema.positive().isValid(null); // true
        assertTrue(actual);

        schema.required();

        actual = schema.isValid(null); // false
        assertFalse(actual);

        actual = schema.isValid(10); // true
        assertTrue(actual);

// Потому что ранее мы вызвали метод positive()
        actual = schema.isValid(-10); // false
        assertFalse(actual);
//  Ноль — не положительное число

        actual = schema.isValid(0); // false
        assertFalse(actual);

        schema.range(5, 10);


        actual = schema.isValid(5); // true
        assertTrue(actual);

        actual = schema.isValid(10); // true
        assertTrue(actual);

        actual = schema.isValid(4); // false
        assertFalse(actual);

        actual = schema.isValid(11); // false
        assertFalse(actual);

        var longValidator = v.number().required().positive().range(0, 99);
        var number = 77;
        actual = longValidator.isValid(number);
        assertTrue(actual);

        number = 999;
        actual = longValidator.isValid(number);
        assertFalse(actual);

    }

    @Test
    public void testMap() {
        System.out.println("TEST MAP");

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


        schema.sizeOf(2);

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
