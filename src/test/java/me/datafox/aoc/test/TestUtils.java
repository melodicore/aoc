package me.datafox.aoc.test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author datafox
 */
public class TestUtils {
    public static void test(int year, int day, String part) {
        System.out.println(assertDoesNotThrow(() ->
                Class.forName(String.format("me.datafox.aoc.aoc%s.Day%s", year, day))
                        .getMethod("solve" + part, URL.class)
                        .invoke(null, res(year, day))));
    }

    public static URL res(int year, int day) {
        return TestUtils.class.getResource(String.format("/%s/day%s.txt", year, day));
    }
}
