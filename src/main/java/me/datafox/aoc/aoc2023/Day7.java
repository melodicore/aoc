package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 7 solutions.
 *
 * @author datafox
 */
public class Day7 {
    private static final List<Character> CARDS = List.of(
            'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final List<Predicate<String>> HANDS = List.of(
            Day7::isFive, Day7::isFour, Day7::isHouse, Day7::isThree, Day7::isTwoPairs, Day7::isPair);

    public static long solve1(URL url) {
        Map<String,Integer> hands = FileUtils.linesAsStream(url)
                .map(s -> s.split(" +"))
                .collect(Collectors.toMap(
                        s -> s[0],
                        s -> Integer.parseInt(s[1])));
        List<String> sorted = new ArrayList<>(hands.keySet());
        sorted.sort(Day7::sortHands);
        int score = 0;
        for(int i = 0; i < sorted.size(); i++) {
            score += hands.get(sorted.get(i)) * (i + 1);
        }
        return score;
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static int sortHands(String s1, String s2) {
        for(Predicate<String> p : HANDS) {
            if(p.test(s1)) {
                if(p.test(s2)) {
                    break;
                }
                return 1;
            } else if(p.test(s2)) {
                return -1;
            }
        }
        for(int i = 0; i < 5; i++) {
            int compare = Integer.compare(CARDS.indexOf(s2.charAt(i)), CARDS.indexOf(s1.charAt(i)));
            if(compare != 0) {
                return compare;
            }
        }
        assert false;
        return 0;
    }

    private static boolean isFive(String str) {
        Map<Character,Integer> map = toCardMap(str);
        return map.size() == 1;
    }

    private static boolean isFour(String str) {
        Map<Character,Integer> map = toCardMap(str);
        return map.values().stream().anyMatch(i -> i == 4);
    }

    private static boolean isHouse(String str) {
        Map<Character,Integer> map = toCardMap(str);
        if(map.size() == 2) {
            int i = map.values().iterator().next();
            return i == 2 || i == 3;
        }
        return false;
    }

    private static boolean isThree(String str) {
        Map<Character,Integer> map = toCardMap(str);
        return map.values().stream().anyMatch(i -> i == 3);
    }

    private static boolean isTwoPairs(String str) {
        Map<Character,Integer> map = toCardMap(str);
        if(map.size() == 3) {
            boolean twice = false;
            for(int i : map.values()) {
                if(i == 2) {
                    if(twice) {
                        return true;
                    } else {
                        twice = true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPair(String str) {
        Map<Character,Integer> map = toCardMap(str);
        return map.values().stream().anyMatch(i -> i == 2);
    }

    private static Map<Character,Integer> toCardMap(String str) {
        Map<Character,Integer> map = new HashMap<>();
        for(char c : str.toCharArray()) {
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        return map;
    }
}
