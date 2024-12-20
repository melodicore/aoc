package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2023 day 18 solutions.
 *
 * @author datafox
 */
public class Day18 {
    private static final Set<Direction> UD = Set.of(Direction.UP, Direction.DOWN);

    private static int minX = 0, minY = 0, maxX = 0, maxY = 0;

    public static int solve1(URL url) {
        List<Trench> trenches = parseTrenches(FileUtils
                .linesAsStream(url)
                .map(s -> s.split(" "))
                .toList());
        return calculateSize(trenches);
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static List<Trench> parseTrenches(List<String[]> rules) {
        Coordinate first = new Coordinate(0, 0);
        Coordinate last = first;
        List<Trench> trenches = new ArrayList<>();
        for(int i = 0; i < rules.size(); i++) {
            String[] split = rules.get(i);
            assert split.length == 3;
            Direction dir = getDir(split[0]);
            assert dir != null;
            for(int j = 0; j < Integer.parseInt(split[1]) - 1; j++) {
                last = last.move(dir, 1);
                trenches.add(new Trench(last, Set.of(dir, dir.opposite())));
            }
            if(i + 1 < rules.size()) {
                Direction next = getDir(rules.get(i + 1)[0]);
                last = last.move(dir, 1);
                setMinMax(last);
                assert next != null;
                trenches.add(new Trench(last, Set.of(dir.opposite(), next)));
            } else {
                trenches.addFirst(new Trench(first, Set.of(dir.opposite(), getDir(rules.getFirst()[0]))));
            }
        }
        return trenches;
    }

    private static Direction getDir(String s) {
        return switch(s) {
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            case "L" -> Direction.LEFT;
            case "R" -> Direction.RIGHT;
            default -> null;
        };
    }

    private static void setMinMax(Coordinate coord) {
        if(coord.x() < minX) {
            minX = coord.x();
        }
        if(coord.y() < minY) {
            minY = coord.y();
        }
        if(coord.x() > maxX) {
            maxX = coord.x();
        }
        if(coord.y() > maxY) {
            maxY = coord.y();
        }
    }

    private static int calculateSize(List<Trench> trenches) {
        Map<Coordinate,Trench> map = trenches.stream().collect(Collectors.toMap(Trench::coord, Function.identity()));
        boolean inside;
        int count = 0;
        for(int y = minY; y <= maxY; y++) {
            inside = false;
            for(int x = minX; x <= maxX; x++) {
                Coordinate coord = new Coordinate(x, y);
                Trench trench = map.get(coord);
                if(trench == null) {
                    if(inside) {
                        count++;
                    }
                    continue;
                }
                if(isCorner(trench.dirs())) {
                    Set<Direction> start = trench.dirs();
                    Trench next = null;
                    while(x < maxX) {
                        x++;
                        next = map.get(new Coordinate(x, y));
                        if(isCorner(next.dirs())) {
                            break;
                        }
                    }
                    Set<Direction> copy = new HashSet<>(start);
                    copy.addAll(next.dirs());
                    copy.retainAll(UD);
                    if(copy.size() == 2) {
                        inside = !inside;
                    }
                } else if(UD.equals(trench.dirs())) {
                    inside = !inside;
                }
            }
        }
        return count + trenches.size();
    }

    private static boolean isOpposite(Set<Direction> corner, Set<Direction> other) {
        return corner.stream().noneMatch(other::contains);
    }

    private static boolean isCorner(Set<Direction> dirs) {
        assert dirs.size() == 2;
        return !dirs.contains(dirs.iterator().next().opposite());
    }

    private record Trench(Coordinate coord, Set<Direction> dirs) {}
}
