package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Advent of Code 2024 day 19 solutions.
 *
 * @author datafox
 */
public class Day20 {
    public static long solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        List<Coordinate> track = parseTrack(map);
        return getCheatPaths(track)
                .stream()
                .mapToInt(List::size)
                .map(i -> track.size() - i)
                .filter(i -> i >= 100)
                .count();
    }

    public static long solve2(URL url) {
        return 0;
    }

    private static List<Coordinate> parseTrack(char[][] map) {
        Coordinate start = null;
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(map[i][j] == 'S') {
                    start = new Coordinate(j, i);
                }
            }
        }
        assert start != null;
        List<Coordinate> track = new ArrayList<>();
        track.add(start);
        Direction dir = null;
        while(true) {
            if(dir == null) {
                for(Direction next : Direction.values()) {
                    Coordinate coord = track.getLast().move(next, 1);
                    if(map[coord.y()][coord.x()] == '.') {
                        dir = next;
                        break;
                    }
                }
            }
            Coordinate coord = track.getLast().move(dir, 1);
            if(map[coord.y()][coord.x()] == '#') {
                dir = dir.rotateLeft();
                coord = track.getLast().move(dir, 1);
            }
            if(map[coord.y()][coord.x()] == '#') {
                dir = dir.opposite();
                coord = track.getLast().move(dir, 1);
            }
            if(map[coord.y()][coord.x()] == 'E') {
                track.add(coord);
                break;
            }
            assert map[coord.y()][coord.x()] == '.';
            track.add(coord);
        }
        return track;
    }

    private static List<List<Coordinate>> getCheatPaths(List<Coordinate> track) {
        Set<Coordinate> trackSet = new HashSet<>(track);
        List<List<Coordinate>> cheatPaths = new ArrayList<>();
        for(int i = 1; i < track.size() - 1; i++) {
            Coordinate last = track.get(i - 1);
            Coordinate coord = track.get(i);
            Coordinate next = track.get(i + 1);
            Direction lastDir = getDirection(coord, last);
            Direction nextDir = getDirection(coord, next);
            for(Direction dir : Direction.values()) {
                if(dir.equals(lastDir) || dir.equals(nextDir)) {
                    continue;
                }
                Coordinate cheated = coord.move(dir, 2);
                if(!trackSet.contains(cheated)) {
                    continue;
                }
                int index = track.indexOf(cheated);
                if(index <= i) {
                    continue;
                }
                List<Coordinate> cheatPath = new ArrayList<>(track.subList(0, i + 1));
                cheatPath.add(coord.move(dir, 1));
                cheatPath.addAll(track.subList(index, track.size()));
                cheatPaths.add(cheatPath);
            }
        }
        return cheatPaths;
    }

    private static Direction getDirection(Coordinate from, Coordinate to) {
        Coordinate diff = to.diff(from);
        int xs = Integer.signum(diff.x());
        int ys = Integer.signum(diff.y());
        if(Math.abs(xs) == Math.abs(ys)) {
            return null;
        }
        if(xs != 0) {
            return xs > 0 ? Direction.RIGHT : Direction.LEFT;
        }
        return ys > 0 ? Direction.DOWN : Direction.UP;
    }
}
