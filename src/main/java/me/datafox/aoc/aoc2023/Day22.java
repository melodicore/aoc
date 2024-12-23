package me.datafox.aoc.aoc2023;

import me.datafox.aoc.Coordinate3;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Advent of Code 2023 day 22 solutions.
 *
 * @author datafox
 */
public class Day22 {
    public static long solve1(URL url) {
        List<Block> blocks = FileUtils.linesAsStream(url)
                .map(Block::parseBlock)
                .toList();
        List<Block> fallen = blocks;
        do {
            blocks = fallen;
            fallen = fall(fallen);
        } while(!blocks.equals(fallen));
        List<Block> finalBlocks = blocks;
        return blocks.stream()
                .map(b -> finalBlocks.stream()
                        .filter(Predicate.not(Predicate.isEqual(b)))
                        .toList())
                .filter(l -> l.equals(fall(l)))
                .count();
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Coordinate3 parseCoordinate(String str) {
        String[] split = str.split(",");
        assert split.length == 3;
        return new Coordinate3(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    private static List<Block> fall(List<Block> fallen) {
        List<Block> blocks = new ArrayList<>(fallen);
        Set<Coordinate3> coords = blocks.stream()
                .map(Block::coords)
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(HashSet::new));
        for(int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if(block.coords().stream().anyMatch(c -> c.z() == 1)) {
                continue;
            }
            coords.removeAll(block.coords());
            Block fall = block.fall();
            if(fall.coords().stream().anyMatch(coords::contains)) {
                coords.addAll(block.coords());
                continue;
            }
            blocks.set(i, fall);
            coords.addAll(fall.coords());
        }
        return blocks;
    }

    private record Block(Set<Coordinate3> coords) {
        public static Block parseBlock(String str) {
            String[] split = str.split("~");
            assert split.length == 2;
            Coordinate3 start = parseCoordinate(split[0]);
            Coordinate3 end = parseCoordinate(split[1]);
            if(start.equals(end)) {
                return new Block(Set.of(start));
            }
            Coordinate3 diff = end.diff(start);
            boolean x = diff.x() != 0;
            boolean y = diff.y() != 0;
            boolean z = diff.z() != 0;
            assert !x && !y || !x && !z || !y && !z;
            boolean dir = x ? diff.x() > 0 : y ? diff.y() > 0 : diff.z() > 0;
            int s = x ? start.x() : y ? start.y() : start.z();
            int e = x ? end.x() : y ? end.y() : end.z();
            assert e > s == dir;
            Set<Coordinate3> set = IntStream.rangeClosed(dir ? s : e, dir ? e : s)
                    .mapToObj(i -> new Coordinate3(x ? i : start.x(), y ? i : start.y(), z ? i : start.z()))
                    .collect(Collectors.toSet());
            assert set.contains(start) && set.contains(end);
            return new Block(set);
        }

        public Block fall() {
            return new Block(coords.stream().map(c -> c.move(0, 0, -1)).collect(Collectors.toSet()));
        }

        @Override
        public boolean equals(Object object) {
            if(this == object) return true;
            if(!(object instanceof Block block)) return false;
            return Objects.equals(coords, block.coords);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(coords);
        }
    }
}
