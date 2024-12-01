package me.datafox.aoc2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author datafox
 */
public class FileUtils {
    public static Stream<String> resourceAsStream(URL url) {
        try {
            return Files.lines(Paths.get(url.toURI()));
        } catch(IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
