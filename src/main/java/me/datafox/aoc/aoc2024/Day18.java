package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Advent of Code 2024 day 18 solutions.
 *
 * @author datafox
 */
public class Day18 {
    private static final int WIDTH = 71;
    private static final int HEIGHT = 71;
    private static final int WALLS = 1024;

    public static int solve1(URL url) {
        List<Coordinate> walls = FileUtils.linesAsStream(url)
                .map(s -> s.split(","))
                .map(a -> new Coordinate(
                        Integer.parseInt(a[0]),
                        Integer.parseInt(a[1])))
                .toList();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(WIDTH - 1, HEIGHT - 1);
        List<Coordinate> path = getPath( new HashSet<>(walls.subList(0, WALLS)), start, end);
        return path.size() - 1;
    }

    public static String solve2(URL url) {
        List<Coordinate> walls = FileUtils.linesAsStream(url)
                .map(s -> s.split(","))
                .map(a -> new Coordinate(
                        Integer.parseInt(a[0]),
                        Integer.parseInt(a[1])))
                .toList();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(WIDTH - 1, HEIGHT - 1);
        List<Coordinate> path;
        int count = WALLS;
        do {
            count++;
            path = getPath(new HashSet<>(walls.subList(0, count)), start, end);
        } while(path != null);
        Coordinate coord = walls.get(count - 1);
        return coord.x() + "," + coord.y();
    }

    private static List<Coordinate> getPath(Set<Coordinate> walls, Coordinate start, Coordinate end) {
        Set<Coordinate> visited = new HashSet<>();
        visited.add(start);
        Set<Node> currentIter = new HashSet<>();
        currentIter.add(new Node(start, null));
        while(!visited.contains(end)) {
            Set<Node> nextIter = new HashSet<>();
            for(Node current : currentIter) {
                for(Direction dir : Direction.values()) {
                    Coordinate next = current.coord().move(dir, 1);
                    if(walls.contains(next) ||
                            visited.contains(next) ||
                            !next.isWithinBounds(0, 0, WIDTH - 1, HEIGHT - 1)) {
                        continue;
                    }
                    Node node = new Node(next, current);
                    nextIter.add(node);
                    visited.add(node.coord());
                }
            }
            if(nextIter.isEmpty()) {
                return null;
            }
            currentIter.clear();
            currentIter.addAll(nextIter);
        }
        Node node = currentIter.stream()
                .filter(n -> n.coord().equals(end))
                .findAny()
                .orElseThrow();
        List<Coordinate> path = new LinkedList<>();
        while(node != null) {
            path.addFirst(node.coord());
            node = node.prev();
        }
        return path;
    }

    private record Node(Coordinate coord, Node prev) {}
}
