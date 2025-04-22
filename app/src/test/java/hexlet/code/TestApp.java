package hexlet.code;

import hexlet.code.schemas.Schema;
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
        StringSchema stringValidator = validator.string();

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

    }
}
