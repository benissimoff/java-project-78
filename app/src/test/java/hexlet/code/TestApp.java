package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {
    @Test
    public void testSimple() {

        System.out.println("!!! === testSimple");


        String actual = "app";

        assertEquals("app", actual);
    }

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

    }
}
