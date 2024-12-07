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
        part2();
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
          if (evaluateReport(report)) {
              numberOfSafeReports.incrementAndGet();
          }
        });

        System.out.println("safe reports: " + numberOfSafeReports.get());
    }

    public static boolean evaluateReport(ArrayList<Integer> report) {
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

        return !unsafe;
    }

    /**
     * 🤯 - Brute force but works.
     */
    public static void part2() {
        var input = loadInput();
        var numberOfSafeReports = new AtomicInteger();

        input.forEach(report -> {
          if (evaluateReport(report)) {
              numberOfSafeReports.getAndIncrement();
          } else {
              for (var i = 0; i < report.size(); i++) {
                  var testReport = new ArrayList<>(report);
                  testReport.remove(i);
                  if (evaluateReport(testReport)) {
                      numberOfSafeReports.getAndIncrement();
                      break;
                  }
              }
          }
        });

        System.out.println("safe reports w/ one infringement: " + numberOfSafeReports.get());
    }
}
