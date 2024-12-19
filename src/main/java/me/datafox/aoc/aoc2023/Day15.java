package me.datafox.aoc.aoc2023;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Advent of Code 2023 day 15 solutions.
 *
 * @author datafox
 */
public class Day15 {
    public static int solve1(URL url) {
        return Arrays.stream(FileUtils.string(url).split(","))
                .mapToInt(Day15::hash)
                .sum();
    }

    public static int solve2(URL url) {
        Node[] nodes = new Node[256];
        for(String s : FileUtils.string(url).split(",")) {
            String[] split = s.split("[-=]", 2);
            int hash = hash(split[0]);
            if(split[1].isEmpty()) {
                if(nodes[hash] != null) {
                    Node node = nodes[hash];
                    if(node.key.equals(split[0])) {
                        nodes[hash] = node.next;
                    } else {
                        Node parent = node;
                        while(!node.key.equals(split[0]) && node.next != null) {
                            parent = node;
                            node = node.next;
                        }
                        if(node.key.equals(split[0])) {
                            parent.next = node.next;
                        }
                    }
                }
            } else {
                Node node = null;
                Node parent = nodes[hash];
                do {
                    if(parent == null || parent.key.equals(split[0])) {
                        node = parent;
                        break;
                    }
                    if(parent.next == null) {
                        break;
                    }
                    parent = parent.next;
                } while(true);
                if(node == null) {
                    node = new Node();
                    node.key = split[0];
                    if(parent == null) {
                        nodes[hash] = node;
                    } else {
                        parent.next = node;
                    }
                }
                node.value = Integer.parseInt(split[1]);
            }
        }
        int sum = 0;
        for(int i = 0; i < nodes.length; i++) {
            if(nodes[i] == null) {
                continue;
            }
            int j = 1;
            Node node = nodes[i];
            do {
                sum += (i + 1) * j * node.value;
                node = node.next;
                j++;
            } while(node != null);
        }
        return sum;
    }

    public static int solve2cheeky(URL url) {
        Map<Cheek,Integer> map = new HashMap<>(256, 1f);
        for(String s : FileUtils.string(url).split(",")) {
            String[] split = s.split("[-=]", 2);
            Cheek cheek = new Cheek(split[0]);
            if(split[1].isEmpty()) {
                map.remove(cheek);
            } else {
                map.put(cheek, Integer.parseInt(split[1]));
            }
        }
        int bucket = -1;
        int count = -1;
        int sum = 0;
        for(Map.Entry<Cheek,Integer> e : map.entrySet()) {
            if(e.getKey().hash() != bucket) {
                count = 1;
                bucket = e.getKey().hash();
            }
            sum += (bucket + 1) * count * e.getValue();
            count++;
        }
        return sum;
    }

    private static int hash(String s) {
        int hash = 0;
        for(int i : s.toCharArray()) {
            hash += i;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

    private static class Node {
        public String key;

        public int value;

        public Node next;
    }

    private record Cheek(String id, int hash) {
        private Cheek(String id) {
            this(id, Day15.hash(id));
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Cheek other && id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
