package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.Direction;
import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.*;

/**
 * Advent of Code 2024 day 16 solutions.
 *
 * @author datafox
 */
public class Day16 {
    public static int solve1(URL url) {
        char[][] map = FileUtils.linesAsStream(url)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        Map<Character,Set<Coordinate>> objects = parseObjects(map);
        Coordinate start = objects.get('S').iterator().next();
        Coordinate end = objects.get('E').iterator().next();
        Set<Coordinate> paths = new HashSet<>(objects.get('.'));
        paths.add(start);
        paths.add(end);
        return getPath(paths, start, end);
    }

    public static int solve2(URL url) {
        return 0;
    }

    private static Map<Character,Set<Coordinate>> parseObjects(char[][] map) {
        Map<Character,Set<Coordinate>> objects = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                char c = map[i][j];
                if(!objects.containsKey(c)) {
                    objects.put(c, new HashSet<>());
                }
                objects.get(c).add(new Coordinate(j, i));
            }
        }
        return objects;
    }

    private static int getPath(Set<Coordinate> paths, Coordinate start, Coordinate end) {
        Set<DirCoord> visited = new HashSet<>();
        PriorityQueue<Path> currentPaths = new PriorityQueue<>();
        currentPaths.add(Path.start(start));
        visited.add(currentPaths.peek().coord());
        Path bestPath = null;
        while(!currentPaths.isEmpty()) {
            Path current = currentPaths.poll();
            for(DirCoord next : current.coord().options()) {
                if(next.coord().equals(end)) {
                    if(bestPath == null) {
                        bestPath = current.next(next);
                    } else {
                        Path path = current.next(next);
                        if(path.score() < bestPath.score()) {
                            bestPath = path;
                        }
                    }
                    continue;
                }
                if(!paths.contains(next.coord())) {
                    continue;
                }
                Path nextPath = current.next(next);
                if(visited.contains(next)) {
                    Optional<Path> other = currentPaths.stream().filter(p -> p.find(next) != null).findAny();
                    if(other.isPresent()) {
                        Path compared = other.get().find(next);
                        if(compared.score() <= nextPath.score()) {
                            continue;
                        }
                        nextPath = other.get().splice(nextPath);
                        currentPaths.remove(other.get());
                    } else {
                        continue;
                    }
                }
                visited.add(next);
                currentPaths.add(nextPath);
            }
        }
        return bestPath == null ? 0 : bestPath.score();
    }

    private record DirCoord(Coordinate coord, Direction dir) {
        public DirCoord move() {
            return new DirCoord(coord.move(dir, 1), dir);
        }

        public DirCoord rotateLeft() {
            return new DirCoord(coord, dir.rotateLeft());
        }

        public DirCoord rotateRight() {
            return new DirCoord(coord, dir.rotateRight());
        }

        public Set<DirCoord> options() {
            return Set.of(move(), rotateLeft().move(), rotateRight().move());
        }
    }

    private record Path(DirCoord coord, int score, Path previous) implements Comparable<Path> {
        public Path next(DirCoord next) {
            boolean rotated = !coord.dir().equals(next.dir());
            boolean moved = !coord.coord().equals(next.coord());
            int nextScore = score;
            if(rotated) {
                nextScore += 1000;
            }
            if(moved) {
                nextScore++;
            }
            return new Path(next, nextScore, this);
        }

        public Path find(DirCoord target) {
            Path result = this;
            while(result != null && !result.coord.equals(target)) {
                result = result.previous();
            }
            return result;
        }

        public static Path start(Coordinate start) {
            return new Path(new DirCoord(start, Direction.RIGHT), 0, null);
        }

        public Path splice(Path next) {
            Deque<DirCoord> coords = new LinkedList<>();
            Path current = this;
            while(current != null && !current.coord.equals(next.coord)) {
                coords.addFirst(current.coord);
                current = previous;
            }
            if(current == null) {
                return null;
            }
            for(DirCoord nextCoord : coords) {
                current = current.next(nextCoord);
            }
            return current;
        }

        @Override
        public int compareTo(Path o) {
            return Integer.compare(score, o.score);
        }
    }
}
