package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return 0;
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
}
