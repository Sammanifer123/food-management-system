package com.example.customermanagment;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkPassword() {
        assertTrue(isValidPassword("fd%^&fdsHIOF54GJFIOs"));
        assertTrue(isValidPassword("Assdsd123@1233:'"));
        assertTrue(isValidPassword("dFHJSsdh123@475?;"));
        assertTrue(isValidPassword("Samandi12@#$%1"));
        assertTrue(isValidPassword("f785BNMfd)<@"));
        assertTrue(isValidPassword("Sadbfj123@135.2"));
        assertTrue(isValidPassword("ghdjfgjsd12AS%$^"));
        assertTrue(isValidPassword("Sammani.,123@sd"));
        assertTrue(isValidPassword("djsgfhjsSSSGG<<+,156"));
        assertTrue(isValidPassword("FFNNdnfjgkf@!,*^*354"));

        assertFalse(isValidPassword("fjds"));
        assertFalse(isValidPassword("12355"));
        assertFalse(isValidPassword("ASSD"));
        assertFalse(isValidPassword("fjdsfjkdfbjdbfkjds"));
        assertFalse(isValidPassword("12345352421"));
        assertFalse(isValidPassword("?>:*&*!@#$"));
        assertFalse(isValidPassword(""));
        assertFalse(isValidPassword("sammani123"));
        assertFalse(isValidPassword("SADAAHA!@#123"));
        assertFalse(isValidPassword(" 1234!@#$@$ "));
    }

    private boolean isValidPassword(String password) {
        String patternString = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

