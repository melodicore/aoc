package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

/**
 * Advent of Code 2023 day 23 solutions.
 *
 * @author datafox
 */
public class Day23 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Coordinate,Character> trail = parseTrail(map);
        return getTrails(trail).stream().mapToInt(SequencedSet::size).max().orElse(-1) - 1;
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map<Coordinate,Character> parseTrail(char[][] map) {
        Map<Coordinate,Character> trail = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                char c = map[i][j];
                if(c == '#') {
                    continue;
                }
                if(i == 0) {
                    c = 'S';
                } else if(i == map.length - 1) {
                    c = 'E';
                }
                Coordinate coord = new Coordinate(j, i);
                trail.put(coord, c);
            }
        }
        return trail;
    }

    private static List<SequencedSet<Coordinate>> getTrails(Map<Coordinate,Character> trail) {
        Coordinate start = trail.entrySet()
                .stream()
                .filter(e -> e.getValue() == 'S')
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
        Coordinate target = trail.entrySet()
                .stream()
                .filter(e -> e.getValue() == 'E')
                .map(Map.Entry::getKey)
                .findAny()
                .orElseThrow();
        List<SequencedSet<Coordinate>> trails = new ArrayList<>();
        trails.add(new LinkedHashSet<>());
        trails.getFirst().add(start);
        while(!trails.stream().map(SequencedCollection::getLast).allMatch(Predicate.isEqual(target))) {
            for(SequencedSet<Coordinate> current : new ArrayList<>(trails)) {
                Coordinate coord = current.getLast();
                if(coord.equals(target)) {
                    continue;
                }
                boolean first = true;
                for(Direction dir : Direction.values()) {
                    Coordinate next = coord.move(dir, 1);
                    if(current.contains(next) || !trail.containsKey(next)) {
                        continue;
                    }
                    char c = trail.get(next);
                    if(c != '.' && c != 'S' && c != 'E') {
                        if(!dir.equals(switch(c) {
                            case '^' -> Direction.UP;
                            case 'v' -> Direction.DOWN;
                            case '<' -> Direction.LEFT;
                            case '>' -> Direction.RIGHT;
                            default -> throw new RuntimeException(String.valueOf(c));
                        })) {
                            continue;
                        }
                    }
                    if(first) {
                        current.add(next);
                        first = false;
                    } else {
                        SequencedSet<Coordinate> path = new LinkedHashSet<>(current);
                        path.removeLast();
                        path.add(next);
                        trails.add(path);
                    }
                }
                if(first) {
                    trails.remove(current);
                }
            }
        }
        return trails;
    }
}
