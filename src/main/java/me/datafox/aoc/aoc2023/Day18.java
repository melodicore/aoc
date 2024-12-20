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

    private static final Map<Set<Direction>,Set<Direction>> DIRS;

    static {
        DIRS = new HashMap<>();
        for(Direction dir1 : Direction.values()) {
            for(Direction dir2 : Direction.values()) {
                if(dir1.equals(dir2)) {
                    continue;
                }
                Set<Direction> set = EnumSet.of(dir1, dir2);
                DIRS.put(set, set);
            }
        }
    }

    private static int minX = 0, minY = 0, maxX = 0, maxY = 0;

    private static long hor = 0;

    public static int solve1(URL url) {
        List<Trench> trenches = parseTrenches(FileUtils
                .linesAsStream(url)
                .map(s -> s.split(" "))
                .toList(), false);
        return calculateSize(trenches);
    }

    public static long solve2(URL url) {
        List<Trench> trenches = parseTrenches(FileUtils
                .linesAsStream(url)
                .map(s -> s.split(" ")[2])
                .map(Day18::hexToInput)
                .toList(), true);
        return calculateLargeSize(trenches);
    }

    private static String[] hexToInput(String s) {
        String dir = switch(s.charAt(s.length() - 2)) {
            case '0' -> "R";
            case '1' -> "D";
            case '2' -> "L";
            case '3' -> "U";
            default -> null;
        };
        String count = Integer.toString(Integer.parseInt(s.substring(2, s.length() - 2), 16));
        return new String[] { dir, count, "" };
    }

    private static List<Trench> parseTrenches(List<String[]> rules, boolean skipHor) {
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
                if(skipHor && i != 0 && !UD.contains(dir)) {
                    hor++;
                    continue;
                }
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

    private static boolean isCorner(Set<Direction> dirs) {
        assert dirs.size() == 2;
        return !dirs.contains(dirs.iterator().next().opposite());
    }

    private static long calculateLargeSize(List<Trench> trenches) {
        Map<Integer,List<Trench>> map = new TreeMap<>();
        for(Trench trench : trenches) {
            if(!UD.equals(trench.dirs()) && !isCorner(trench.dirs())) {
                continue;
            }
            if(!map.containsKey(trench.coord().y())) {
                map.put(trench.coord().y(), new ArrayList<>());
            }
            map.get(trench.coord().y()).add(trench);
        }
        long count = 0;
        for(List<Trench> list : map.values()) {
            list.sort(Comparator.comparingInt(t -> t.coord().x()));
            Trench last = null;
            boolean inside = false;
            for(int i = 0; i < list.size(); i++) {
                Trench trench = list.get(i);
                if(inside) {
                    assert !trench.dirs().contains(Direction.LEFT);
                    count += trench.coord().x() - last.coord().x() - 1;
                    if(trench.dirs().contains(Direction.RIGHT)) {
                        i++;
                        Trench next = list.get(i);
                        assert next.dirs().contains(Direction.LEFT);
                        if(trench.dirs().contains(Direction.UP) != next.dirs().contains(Direction.UP)) {
                            inside = false;
                        }
                        last = next;
                        continue;
                    } else {
                        inside = false;
                    }
                } else {
                    assert !trench.dirs().contains(Direction.LEFT);
                    if(trench.dirs().contains(Direction.RIGHT)) {
                        i++;
                        Trench next = list.get(i);
                        assert next.dirs().contains(Direction.LEFT);
                        if(trench.dirs().contains(Direction.UP) != next.dirs().contains(Direction.UP)) {
                            inside = true;
                        }
                        last = next;
                        continue;
                    } else {
                        inside = true;
                    }
                }
                last = trench;
            }
        }
        return count + trenches.size() + hor;
    }

    private record Trench(Coordinate coord, Set<Direction> dirs) {
        public Trench(Coordinate coord, Set<Direction> dirs) {
            this.coord = coord;
            this.dirs = DIRS.getOrDefault(dirs, dirs);
        }
    }
}
