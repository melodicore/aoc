package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2024 day 21 solutions.
 *
 * @author datafox
 */
public class Day21 {
    private static final Map<Character,Coordinate> NUMBERS = new HashMap<>();

    private static final Map<Character,Coordinate> ARROWS = new HashMap<>();

    static {
        NUMBERS.put('7', new Coordinate(0, 0));
        NUMBERS.put('8', new Coordinate(1, 0));
        NUMBERS.put('9', new Coordinate(2, 0));
        NUMBERS.put('4', new Coordinate(0, 1));
        NUMBERS.put('5', new Coordinate(1, 1));
        NUMBERS.put('6', new Coordinate(2, 1));
        NUMBERS.put('1', new Coordinate(0, 2));
        NUMBERS.put('2', new Coordinate(1, 2));
        NUMBERS.put('3', new Coordinate(2, 2));
        NUMBERS.put('0', new Coordinate(1, 3));
        NUMBERS.put('A', new Coordinate(2, 3));

        ARROWS.put('^', new Coordinate(1, 0));
        ARROWS.put('A', new Coordinate(2, 0));
        ARROWS.put('<', new Coordinate(0, 1));
        ARROWS.put('v', new Coordinate(1, 1));
        ARROWS.put('>', new Coordinate(2, 1));
    }


    public static int solve1(URL url) {
        List<String> inputs = FileUtils.linesAsStream(url).toList();
        List<List<String>> outputs = inputs.stream()
                .map(Day21::transformCode)
                .map(Day21::getShortest)
                .map(l -> l.stream().map(Day21::transformArrows).flatMap(List::stream).toList())
                .map(Day21::getShortest)
                .map(l -> l.stream().map(Day21::transformArrows).flatMap(List::stream).toList())
                .map(Day21::getShortest)
                .toList();
        int score = 0;
        for(int i = 0; i < inputs.size(); i++) {
            score += Integer.parseInt(inputs.get(i).substring(0, 3)) * outputs.get(i).getFirst().length();
        }
        return score;
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static List<String> getShortest(List<String> list) {
        List<String> out = new ArrayList<>();
        int length = Integer.MAX_VALUE;
        for(String s : list) {
            if(s.length() < length) {
                out.clear();
                length = s.length();
            }
            if(s.length() == length) {
                out.add(s);
            }
        }
        return out;
    }

    private static List<String> transformCode(String str) {
        return transform(str, NUMBERS);
    }

    private static List<String> transformArrows(String str) {
        return transform(str, ARROWS);
    }

    private static List<String> transform(String str, Map<Character,Coordinate> map) {
        StringBuilder sb = new StringBuilder();
        int emptyY = map.size() == 11 ? 3 : 0;
        Coordinate current = map.get('A');
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            Coordinate next = map.get(c);
            assert next != null;
            Coordinate diff = next.diff(current);
            if(current.x() == 0 && next.y() == emptyY) {
                sb.append("?");
            } else if(next.x() == 0 && current.y() == emptyY) {
                sb.append("!");
            }
            sb.append(arrows(diff));
            int presses = 1;
            while(i + 1 < str.length() && str.charAt(i + 1) == c) {
                i++;
                presses++;
            }
            sb.append("A".repeat(presses));
            current = next;
        }
        return iterations(sb.toString());
    }

    private static List<String> iterations(String str) {
        List<StringBuilder> list = new ArrayList<>();
        list.add(new StringBuilder());
        for(String s : str.split("A")) {
            if(s.length() > 1) {
                char c = s.charAt(0);
                Boolean limited = null;
                if(c == '!' || c == '?') {
                    limited = c == '!';
                    s = s.substring(1);
                    c = s.charAt(0);
                }
                int index = -1;
                for(int i = 1; i < s.length(); i++) {
                    if(s.charAt(i) != c) {
                        index = i;
                        break;
                    }
                }
                if(index != -1) {
                    List<StringBuilder> temp = new ArrayList<>();
                    String s1 = s.substring(0, index);
                    String s2 = s.substring(index);
                    if(limited != null) {
                        if(limited) {
                            list.forEach(sb -> sb.append(s1).append(s2));
                        } else {
                            list.forEach(sb -> sb.append(s2).append(s1));
                        }
                    } else {
                        for(StringBuilder sb : list) {
                            temp.add(new StringBuilder(sb).append(s1).append(s2));
                            temp.add(new StringBuilder(sb).append(s2).append(s1));
                        }
                        list = temp;
                    }
                } else {
                    String temp = s;
                    list.forEach(sb -> sb.append(temp));
                }
            } else {
                String temp = s;
                list.forEach(sb -> sb.append(temp));
            }
            list.forEach(sb -> sb.append("A"));
        }

        return list.stream().map(StringBuilder::toString).toList();
    }

    private static String arrows(Coordinate diff) {
        String str = "";
        if(diff.y() < 0) {
            str += "^".repeat(-diff.y());
        } else if(diff.y() > 0) {
            str += "v".repeat(diff.y());
        }
        if(diff.x() < 0) {
            str += "<".repeat(-diff.x());
        } else if(diff.x() > 0) {
            str += ">".repeat(diff.x());
        }
        return str;
    }
}
