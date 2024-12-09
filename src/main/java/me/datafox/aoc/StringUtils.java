package me.datafox.aoc;

/**
 * @author datafox
 */
public class StringUtils {
    public static String reverse(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }
}
