package me.datafox.aoc.aoc2024;

import me.datafox.aoc.Coordinate;
import me.datafox.aoc.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advent of Code 2024 day 14 solutions.
 *
 * @author datafox
 */
public class Day14 {
    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;

    public static int solve1(URL url) {
        Set<Robot> robots = FileUtils.linesAsStream(url)
                .map(Robot::parse)
                .map(r -> r.move(100))
                .map(r -> r.wrap(WIDTH, HEIGHT))
                .collect(Collectors.toSet());
        return splitQuadrants(robots, WIDTH, HEIGHT).stream().mapToInt(Set::size).reduce(1, (i, j) -> i * j);
    }

    public static int solve2(URL url) {
        Set<Robot> robots = FileUtils.linesAsStream(url)
                .map(Robot::parse)
                .collect(Collectors.toSet());
        Lock lock = new Lock();
        JFrame frame = new JFrame();
        BufferedImage image = drawRobots(robots);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.addKeyListener(new Listener(lock));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized(lock) {
                    lock.action = Action.EXIT;
                    lock.notifyAll();
                }
                super.windowClosed(e);
            }
        });
        frame.setVisible(true);
        int totalSteps = 0;
        loop: while(true) {
            synchronized(lock) {
                try {
                    lock.wait();
                    int steps;
                    switch(lock.action) {
                        case NEXT -> steps = 1;
                        case PREVIOUS -> steps = -1;
                        case NEXT_10 -> steps = 10;
                        case PREVIOUS_10 -> steps = -10;
                        case NEXT_101 -> steps = 101;
                        case EXIT -> {
                            break loop;
                        }
                        default -> steps = 0;
                    }
                    if(steps != 0) {
                        totalSteps += steps;
                        robots = robots.stream()
                                .map(r -> r.move(steps))
                                .map(r -> r.wrap(WIDTH, HEIGHT))
                                .collect(Collectors.toSet());
                        image = drawRobots(robots);
                        label.setIcon(new ImageIcon(image));
                    }
                } catch(InterruptedException ignored) {}
            }
        }
        frame.dispose();
        return totalSteps;
    }

    private static BufferedImage drawRobots(Set<Robot> robots) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for(Robot r : robots) {
            image.setRGB(r.coord().x(), r.coord().y(), 0xFFFFFF);
        }
        return image;
    }

    private static List<Set<Robot>> splitQuadrants(Set<Robot> robots, int width, int height) {
        Set<Robot> ul = new HashSet<>();
        Set<Robot> ur = new HashSet<>();
        Set<Robot> dl = new HashSet<>();
        Set<Robot> dr = new HashSet<>();
        int hw = width / 2;
        int hh = height / 2;
        boolean ow = width % 2 == 1;
        boolean oh = height % 2 == 1;
        robots.forEach(r -> {
            Coordinate c = r.coord();
            if(c.x() < hw) {
                if(c.y() < hh) {
                    ur.add(r);
                } else if(!oh || c.y() > hh) {
                    ul.add(r);
                }
            } else if(!ow || c.x() > hw) {
                if(c.y() < hh) {
                    dr.add(r);
                } else if(!ow || c.y() > hh) {
                    dl.add(r);
                }
            }
        });
        return List.of(ul, ur, dl, dr);
    }

    private record Robot(Coordinate coord, Coordinate speed) {
        public static Robot parse(String str) {
            String[] split = str.substring(2).split(",| v=", 4);
            assert split.length == 4;
            return new Robot(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
                    new Coordinate(Integer.parseInt(split[2]), Integer.parseInt(split[3])));
        }

        public Robot move(int steps) {
            return new Robot(coord.move(speed.multiply(steps)), speed);
        }

        public Robot wrap(int width, int height) {
            return new Robot(coord.modulo(width, height), speed);
        }
    }

    private static class Listener implements KeyListener {
        private final Lock lock;

        public Listener(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            synchronized(lock) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> {
                        lock.action = Action.NEXT;
                        lock.notifyAll();
                    }
                    case KeyEvent.VK_LEFT -> {
                        lock.action = Action.PREVIOUS;
                        lock.notifyAll();
                    }
                    case KeyEvent.VK_UP -> {
                        lock.action = Action.NEXT_10;
                        lock.notifyAll();
                    }
                    case KeyEvent.VK_DOWN -> {
                        lock.action = Action.PREVIOUS_10;
                        lock.notifyAll();
                    }
                    case KeyEvent.VK_SPACE -> {
                        lock.action = Action.NEXT_101;
                        lock.notifyAll();
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        lock.action = Action.EXIT;
                        lock.notifyAll();
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }


    }

    private static class Lock {
        public Action action = Action.NEXT;
    }

    private enum Action {
        NEXT,
        PREVIOUS,
        NEXT_10,
        PREVIOUS_10,
        NEXT_101,
        EXIT
    }
}
