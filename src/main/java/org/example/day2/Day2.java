package org.example.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day2 {
    enum Direction {
        DECREASING,
        INCREASING
    }

    public static void main(String[] args) {
        part1();
    }

    public static ArrayList<ArrayList<Integer>> loadInput() {
        var input = new ArrayList<ArrayList<Integer>>();

        try {
            var loader = Day2.class.getClassLoader().getResource("day2/input.txt");
            assert loader != null;

            var path = Paths.get(loader.toURI());
            try (var lines = Files.lines(path)) {
                lines.forEach(line -> {
                    var levels = Arrays.stream(line.split("\\s+"));
                    input.add(levels.map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new)));
                });
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static void part1() {
        var input = loadInput();
        var numberOfSafeReports = new AtomicInteger();

        input.forEach(report -> {
            Direction direction = null;
            var unsafe = false;
            var previousLevel = report.getFirst();
            for (var i = 1; i < report.size(); i++) {
                var currentLevel = report.get(i);
                if (currentLevel > previousLevel) {
                    if (direction == Direction.DECREASING) {
                        unsafe = true;
                    }
                    direction = Direction.INCREASING;
                } else if (currentLevel < previousLevel) {
                    if (direction == Direction.INCREASING) {
                        unsafe = true;
                    }
                    direction = Direction.DECREASING;
                }

                var diff = Math.abs(currentLevel - previousLevel);
                if (diff < 1 || diff > 3) {
                    unsafe = true;
                }

                previousLevel = currentLevel;
            }

            if (!unsafe) {
                numberOfSafeReports.getAndIncrement();
            }
        });

        System.out.println(numberOfSafeReports.get());
    }
}
