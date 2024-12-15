package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advent of Code 2024 day 15 solutions.
 *
 * @author datafox
 */
public class Day15 {
    public static int solve1(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        char[][] map = split[0].lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        List<Set<Coordinate>> obstacles = parseObstacles(map);
        Set<Coordinate> walls = obstacles.get(0);
        Set<Coordinate> boxes = obstacles.get(1);
        Coordinate robot = obstacles.get(2).iterator().next();
        String moves = split[1].replaceAll("\n", "");
        moveRobot(walls, boxes, robot, moves);
        return boxes.stream().mapToInt(c -> c.x() + c.y() * 100).sum();
    }

    public static int solve2(URL url) {
        String[] split = FileUtils.string(url).split("\n\n");
        char[][] map = split[0].lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        List<Set<Coordinate>> obstacles = parseObstacles(map);
        Set<Coordinate> walls = obstacles.get(0).stream()
                .map(c -> c.move(c.x(), 0))
                .map(WideBox::new)
                .flatMap(WideBox::coords)
                .collect(Collectors.toSet());
        Set<WideBox> boxes = obstacles.get(1).stream()
                .map(c -> c.move(c.x(), 0))
                .map(WideBox::new)
                .collect(Collectors.toCollection(HashSet::new));
        Coordinate robot = obstacles.get(2).iterator().next();
        robot = robot.move(robot.x(), 0);
        String moves = split[1].replaceAll("\n", "");
        moveRobotWide(walls, boxes, robot, moves);
        return boxes.stream().map(WideBox::left).mapToInt(c -> c.x() + c.y() * 100).sum();
    }

    private static List<Set<Coordinate>> parseObstacles(char[][] map) {
        Set<Coordinate> walls = new HashSet<>();
        Set<Coordinate> boxes = new HashSet<>();
        Set<Coordinate> robot = new HashSet<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                char c = map[i][j];
                if(c == '.') {
                    continue;
                }
                Coordinate coord = new Coordinate(j, i);
                if(c == '@') {
                    assert robot.isEmpty();
                    robot.add(coord);
                } else {
                (c == 'O' ? boxes : walls).add(coord);
                }
            }
        }
        return List.of(walls, boxes, robot);
    }

    private static void moveRobot(Set<Coordinate> walls, Set<Coordinate> boxes, Coordinate robot, String moves) {
        for(char c : moves.toCharArray()) {
            Direction dir = switch(c) {
                case '>' -> Direction.RIGHT;
                case 'v' -> Direction.DOWN;
                case '<' -> Direction.LEFT;
                case '^' -> Direction.UP;
                default -> null;
            };
            assert dir != null;
            Coordinate next = robot.move(dir, 1);
            if(walls.contains(next)) {
                continue;
            }
            if(boxes.contains(next)) {
                if(!moveBox(next, dir, walls, boxes)) {
                    continue;
                }
            }
            robot = next;
        }
    }

    private static boolean moveBox(Coordinate box, Direction dir, Set<Coordinate> walls, Set<Coordinate> boxes) {
        assert boxes.contains(box);
        Coordinate next = box.move(dir, 1);
        if(walls.contains(next)) {
            return false;
        }
        if(boxes.contains(next)) {
            if(!moveBox(next, dir, walls, boxes)) {
                return false;
            }
        }
        boxes.remove(box);
        boxes.add(next);
        return true;
    }

    private static void moveRobotWide(Set<Coordinate> walls, Set<WideBox> boxes, Coordinate robot, String moves) {
        Set<Coordinate> boxCoords = boxes.stream().flatMap(WideBox::coords).collect(Collectors.toSet());
        for(char c : moves.toCharArray()) {
            Direction dir = switch(c) {
                case '>' -> Direction.RIGHT;
                case 'v' -> Direction.DOWN;
                case '<' -> Direction.LEFT;
                case '^' -> Direction.UP;
                default -> null;
            };
            assert dir != null;
            Coordinate next = robot.move(dir, 1);
            if(walls.contains(next)) {
                continue;
            }
            if(boxCoords.contains(next)) {
                if(!moveBoxWide(next, dir, walls, boxes)) {
                    continue;
                }
                boxCoords = boxes.stream().flatMap(WideBox::coords).collect(Collectors.toSet());
            }
            robot = next;
        }
    }

    private static boolean moveBoxWide(Coordinate coord, Direction dir, Set<Coordinate> walls, Set<WideBox> boxes) {
        WideBox box = new WideBox(coord);
        if(!boxes.contains(box)) {
            box = box.move(Direction.LEFT, 1);
        }
        assert boxes.contains(box);
        WideBox next = box.move(dir, 1);
        if(next.coords().anyMatch(walls::contains)) {
            return false;
        }
        if(dir.equals(Direction.LEFT) || dir.equals(Direction.RIGHT)) {
            WideBox next2 = next.move(dir, 1);
            if(boxes.contains(next2)) {
                if(!moveBoxWide(next2.left(), dir, walls, boxes)) {
                    return false;
                }
            }
        } else {
            if(boxes.contains(next)) {
                if(!moveBoxWide(next.left(), dir, walls, boxes)) {
                    return false;
                }
            } else {
                WideBox next2 = next.move(Direction.LEFT, 1);
                WideBox next3 = next.move(Direction.RIGHT, 1);
                Set<WideBox> copy = new HashSet<>(boxes);
                if(boxes.contains(next2)) {
                    if(!moveBoxWide(next2.left(), dir, walls, copy)) {
                        return false;
                    }
                }
                if(boxes.contains(next3)) {
                    if(!moveBoxWide(next3.left(), dir, walls, copy)) {
                        return false;
                    }
                }
                boxes.clear();
                boxes.addAll(copy);
            }
        }
        boxes.remove(box);
        boxes.add(next);
        return true;
    }

    private record WideBox(Coordinate left, Coordinate right) {
        public WideBox(Coordinate left) {
            this(left, left.move(1, 0));
        }

        public WideBox move(Direction dir, int steps) {
            return new WideBox(left.move(dir, steps));
        }

        public Stream<Coordinate> coords() {
            return Stream.of(left, right);
        }
    }
}
