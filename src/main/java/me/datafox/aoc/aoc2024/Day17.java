package me.datafox.aoc.aoc2024;

import me.datafox.aoc.FileUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 day 17 solutions.
 *
 * @author datafox
 */
public class Day17 {
    private static final Op[] ops = new Op[8];

    static {
        ops[0] = new Op() {
            @Override
            public State apply(State state) {
                int dividend = state.a();
                int divisor = 1;
                for(int i = 0; i < state.combo(); i++) {
                    divisor *= 2;
                }
                return state.next(dividend / divisor, state.b(), state.c());
            }
        };
        ops[1] = new Op() {
            @Override
            public State apply(State state) {
                return state.next(state.a(), state.b() ^ state.operand(), state.c());
            }
        };
        ops[2] = new Op() {
            @Override
            public State apply(State state) {
                return state.next(state.a(), state.combo() % 8, state.c());
            }
        };
        ops[3] = new Op() {
            @Override
            public State apply(State state) {
                if(state.a() == 0) {
                    return state.next(0, state.b(), state.c());
                }
                return new State(state.a(), state.b(), state.c(), state.operand(), state.program());
            }
        };
        ops[4] = new Op() {
            @Override
            public State apply(State state) {
                return state.next(state.a(), state.b() ^ state.c(), state.c());
            }
        };
        ops[5] = new Op() {
            @Override
            public State apply(State state) {
                output.add(state.combo() % 8);
                return state.next(state.a(), state.b(), state.c());
            }
        };
        ops[6] = new Op() {
            @Override
            public State apply(State state) {
                int dividend = state.a();
                int divisor = 1;
                for(int i = 0; i < state.combo(); i++) {
                    divisor *= 2;
                }
                return state.next(state.a(), dividend / divisor, state.c());
            }
        };
        ops[7] = new Op() {
            @Override
            public State apply(State state) {
                int dividend = state.a();
                int divisor = 1;
                for(int i = 0; i < state.combo(); i++) {
                    divisor *= 2;
                }
                return state.next(state.a(), state.b(), dividend / divisor);
            }
        };
    }

    private static List<Integer> output;

    public static String solve1(URL url) {
        List<String> input = FileUtils.linesAsStream(url).toList();
        int a = Integer.parseInt(input.get(0).substring(12));
        int b = Integer.parseInt(input.get(1).substring(12));
        int c = Integer.parseInt(input.get(2).substring(12));
        int[] program = Arrays.stream(input.get(4).substring(9).split(",")).mapToInt(Integer::parseInt).toArray();
        State state = new State(a, b, c, 0, program);
        output = new ArrayList<>();
        while(state != null) {
            state = state.advance();
        }
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static int solve2(URL url) {
        return 0;
    }

    private record State(int a, int b, int c, int op, int[] program) {
        public int operand() {
            return program[op + 1];
        }

        public int combo() {
            int in = operand();
            if(in < 4) {
                return in;
            }
            return switch(in) {
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> throw new RuntimeException();
            };
        }

        public State next(int a, int b, int c) {
            return new State(a, b, c, op + 2, program);
        }

        public State advance() {
            if(op < 0 || op >= program.length) {
                return null;
            }
            return ops[program[op]].apply(this);
        }

        @Override
        public String toString() {
            return "State{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    ", op=" + op +
                    ", program=" + Arrays.toString(program) +
                    '}';
        }
    }

    private interface Op extends Function<State,State> {}
}
