package me.datafox.aoc;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author datafox
 */
public class StreamUtils {
    public static Function<String,Stream<String>> split(String regex) {
        return str -> Arrays.stream(str.split(regex));
    }
}
