package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Advent of Code 2023 day 5 solutions.
 *
 * @author datafox
 */
public class Day5 {
    public static long solve1(URL url) {
        String[] maps = FileUtils.string(url).split("seeds: |\\n\\n.+:\\n");
        long[] seeds = Arrays.stream(maps[1].split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        Range[][] ranges = new Range[maps.length - 2][];
        for(int i = 0; i < maps.length - 2; i++) {
            String str = maps[i + 2];
            ranges[i] = str.lines()
                    .map(Range::parse)
                    .toArray(Range[]::new);
        }
        return LongStream.of(seeds)
                .map(s -> calculatePosition(s, ranges))
                .min()
                .orElse(0);
    }

    public static long solve2(URL url) {
        String[] maps = FileUtils.string(url).split("seeds: |\\n\\n.+:\\n");
        long[] seedInputs = Arrays.stream(maps[1].split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        SeedRange[] seeds = getSeedRanges(seedInputs);
        Range[][] ranges = new Range[maps.length - 2][];
        for(int i = 0; i < maps.length - 2; i++) {
            String str = maps[i + 2];
            ranges[i] = str.lines()
                    .map(Range::parse)
                    .toArray(Range[]::new);
        }
        return Arrays.stream(calculatePositions(seeds, ranges))
                .mapToLong(SeedRange::start)
                .min()
                .orElse(0);
    }

    private static long calculatePosition(long seed, Range[][] ranges) {
        for(Range[] arr : ranges) {
            for(Range range : arr) {
                long temp = range.convert(seed);
                if(temp != seed) {
                    seed = temp;
                    break;
                }
            }
        }
        return seed;
    }

    private static SeedRange[] getSeedRanges(long[] seeds) {
        SeedRange[] ranges = new SeedRange[seeds.length / 2];
        for(int i = 0; i < ranges.length; i++) {
            ranges[i] = new SeedRange(seeds[i * 2], seeds[i * 2 + 1]);
        }
        return ranges;
    }

    private static SeedRange[] calculatePositions(SeedRange[] seeds, Range[][] ranges) {
        for(Range[] arr : ranges) {
            List<SeedRange> completed = new ArrayList<>();
            List<SeedRange> list = new ArrayList<>();
            for(Range range : arr) {
                list.clear();
                for(SeedRange seed : seeds) {
                    SeedRange[] split = seed.split(range);
                    if(split.length == 1) {
                        if(seed.start() >= range.source &&
                                seed.start() + seed.range() <= range.source() + range.range()) {
                            completed.add(seed.convert(range));
                        } else {
                            list.add(seed);
                        }
                    }
                    else if(split.length == 3) {
                        list.add(split[0]);
                        completed.add(split[1].convert(range));
                        list.add(split[2]);
                    } else if(range.convert(seed.start()) == seed.start()) {
                        list.add(split[0]);
                        completed.add(split[1].convert(range));
                    } else {
                        completed.add(split[0].convert(range));
                        list.add(split[1]);
                    }
                }
                seeds = list.toArray(SeedRange[]::new);
            }
            completed.addAll(list);
            seeds = completed.toArray(SeedRange[]::new);
        }
        return seeds;
    }

    private record Range(long dest, long source, long range) {
        public static Range parse(String str) {
            long[] arr = Arrays.stream(str.split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();
            assert arr.length == 3;
            return new Range(arr[0], arr[1], arr[2]);
        }

        public long convert(long input) {
            if(input >= source && input < source + range) {
                return dest + (input - source);
            }
            return input;
        }
    }

    private record SeedRange(long start, long range) {
        public SeedRange[] split(Range r) {
            if(start < r.source) {
                if(start + range <= r.source) {
                    return new SeedRange[] { this };
                } else if(start + range < r.source + r.range) {
                    return split(r.source);
                } else {
                    SeedRange[] split1 = split(r.source);
                    SeedRange[] split2 = split1[1].split(r.source + r.range);
                    return new SeedRange[] { split1[0], split2[0], split2[1] };
                }
            } else {
                if(start >= r.source + r.range) {
                    return new SeedRange[] { this };
                } else if(start + range <= r.source + r.range) {
                    return new SeedRange[] { this };
                } else {
                    return split(r.source + r.range);
                }
            }
        }

        public SeedRange convert(Range r) {
            return new SeedRange(r.convert(start), range);
        }

        private SeedRange[] split(long pos) {
            assert pos > start;
            return new SeedRange[] {
                    new SeedRange(start, pos - start),
                    new SeedRange(pos, range - (pos - start))
            };
        }
    }
}
