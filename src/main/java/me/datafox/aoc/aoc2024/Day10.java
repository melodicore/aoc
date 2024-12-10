package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advent of Code 2024 day 10 solutions.
 *
 * @author datafox
 */
public class Day10 {
    public static long solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Coordinate,Tile> tiles = getTiles(map);
        Map<Coordinate,Tile> heads = connectTiles(tiles, false);
        return heads.values().stream().flatMap(Day10::traverse).count();
    }

    public static long solve2(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Coordinate,Tile> tiles = getTiles(map);
        Map<Coordinate,Tile> heads = connectTiles(tiles, true);
        return heads.values().stream().flatMap(Day10::traverse).count();
    }

    private static Stream<Tile> traverse(Tile tile) {
        if(tile.height() == 9) {
            return Stream.of(tile);
        }
        return tile.next().stream().flatMap(Day10::traverse);
    }

    private static Map<Coordinate,Tile> getTiles(char[][] map) {
        Map<Coordinate,Tile> tiles = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if(!Character.isDigit(map[i][j])) {
                    continue;
                }
                Coordinate coord = new Coordinate(j, i);
                tiles.put(coord, new Tile(coord, Character.getNumericValue(map[i][j])));
            }
        }
        return tiles;
    }

    private static Map<Coordinate,Tile> connectTiles(Map<Coordinate,Tile> tiles, boolean part2) {
        Map<Coordinate,Tile> heads = new HashMap<>(tiles
                .entrySet()
                .stream()
                .filter(e -> e.getValue().height() == 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue)));
        heads.forEach((c, t) -> connectTiles(c, t, tiles, part2 ? null : new HashSet<>()));
        return heads;
    }

    private static boolean connectTiles(Coordinate coord, Tile tile, Map<Coordinate,Tile> tiles, Set<Tile> visitedPeaks) {
        if(tile.height() == 9) {
            if(visitedPeaks == null) {
                return true;
            }
            return visitedPeaks.add(tile);
        }
        boolean successful = false;
        for(Direction dir : Direction.values()) {
            Coordinate other = coord.move(dir, 1);
            if(tiles.containsKey(other)) {
                Tile otherTile = tiles.get(other).copy();
                if(otherTile.height() == tile.height() + 1) {
                    if(connectTiles(other, otherTile, tiles, visitedPeaks)) {
                        tile.next().add(otherTile);
                        successful = true;
                    }
                }
            }
        }
        return successful;
    }

    private record Tile(Coordinate coord, int height, List<Tile> next) {
        public Tile(Coordinate coord, int height) {
            this(coord, height, new ArrayList<>());
        }

        public Tile copy() {
            return new Tile(coord, height, new ArrayList<>(next));
        }
    }
}
