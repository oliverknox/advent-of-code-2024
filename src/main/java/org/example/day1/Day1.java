package org.example.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * <a href="https://adventofcode.com/2024/day/1">Day 1</a>
 */
public class Day1 {
    public static void main(String[] args) {
        var leftList = new ArrayList<Integer>();
        var rightList = new ArrayList<Integer>();
        var distances = new ArrayList<Integer>();

        try {
            var loader = Day1.class.getClassLoader().getResource("day1/input.txt");
            assert loader != null;

            var path = Paths.get(loader.toURI());
            try (var lines = Files.lines(path)) {
                lines.forEach(line -> {
                    var iterator = Arrays.stream(line.split("\\s+")).iterator();
                    leftList.add(Integer.parseInt(iterator.next()));
                    rightList.add(Integer.parseInt(iterator.next()));
                });
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        assert leftList.size() == rightList.size();
        for (var i = 0; i < leftList.size(); i++) {
            distances.add(Math.abs(leftList.get(i) - rightList.get(i)));
        }

        var result = distances.stream().mapToInt(Integer::intValue).sum();

        System.out.println("left: " + leftList);
        System.out.println("right: " + rightList);
        System.out.println("distances: " + distances);
        System.out.println("result: " + result);
    }
}
