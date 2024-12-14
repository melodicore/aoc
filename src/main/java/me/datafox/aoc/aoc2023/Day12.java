package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Advent of Code 2023 day 12 solutions.
 *
 * @author datafox
 */
public class Day12 {
    private static Map<Row,Long> cache;

    public static long solve1(URL url) {
        cache = new HashMap<>();
        return FileUtils.linesAsStream(url)
                .peek(System.out::println)
                .map(Row::parse)
                .peek(System.out::println)
                .mapToLong(Day12::calculatePossibilities)
                .peek(System.out::println)
                .sum();
    }

    public static long solve2(URL url) {
        cache = new HashMap<>();
        return FileUtils.linesAsStream(url)
                .sequential()
                .map(Row::parse)
                .map(r -> r.fold(5))
                .mapToLong(Day12::calculatePossibilities)
                .sum();
    }

    private static long calculatePossibilities(Row row) {
        if(cache.containsKey(row)) {
            return cache.get(row);
        }
        int maxOffset = row.row().length() - Arrays.stream(row.broken()).sum() - row.broken().length + 1;
        long result = 0;
        if(maxOffset == 0) {
            result = row.checkMatch() ? 1 : 0;
        } else {
            outer: for(int i = 0; i <= maxOffset; i++) {
                if(i != 0 && row.row().substring(0, i).contains("#")) {
                    continue;
                }
                int j;
                for(j = i; j < i + row.broken()[0]; j++) {
                    if(row.row().charAt(j) == '.') {
                        continue outer;
                    }
                }
                if(j == row.row().length() || row.row().charAt(j) != '#') {
                    if(row.broken().length == 1) {
                        if(!row.row().substring(j).contains("#")) {
                            result++;
                        }
                    } else {
                        result += calculatePossibilities(row.split(i));
                    }
                }
            }
        }
        cache.put(row, result);
        return result;
    }

    private record Row(String row, int[] broken) {
        @Override
        public boolean equals(Object object) {
            if(this == object) return true;
            if(!(object instanceof Row row1)) return false;

            return row.equals(row1.row) && Arrays.equals(broken, row1.broken);
        }

        @Override
        public int hashCode() {
            int result = row.hashCode();
            result = 31 * result + Arrays.hashCode(broken);
            return result;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "row='" + row + '\'' +
                    ", broken=" + Arrays.toString(broken) +
                    '}';
        }

        public static Row parse(String line) {
            String[] split = line.split(" ");
            return new Row(split[0], Arrays.stream(split[1].split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray());
        }

        public boolean checkMatch() {
            int count = 0;
            for(int i = 0; i < broken.length; i++) {
                if(count != 0) {
                    if(row.charAt(count) == '#') {
                        return false;
                    }
                    count++;
                }
                for(int j = 0; j < broken[i]; j++) {
                    if(row.charAt(count) == '.') {
                        return false;
                    }
                    count++;
                }
            }
            if(count < row.length()) {
                return !row.substring(count).contains("#");
            }
            return true;
        }

        public Row split(int index) {
            return new Row(row.substring(broken[0] + index + 1), Arrays.copyOfRange(broken, 1, broken.length));
        }

        public Row fold(int n) {
            if(n == 1) {
                return this;
            }
            return new Row(IntStream.range(0, n).mapToObj(i -> row).collect(Collectors.joining("?")),
                    IntStream.range(0, n).flatMap(i -> IntStream.of(broken)).toArray());
        }
    }
}
