package org.example.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * <a href="https://adventofcode.com/2024/day/1">Day 1</a>
 */
public class Day1 {
    public static class Input {
        public ArrayList<Integer> left = new ArrayList<>();
        public ArrayList<Integer> right = new ArrayList<>();
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static Input loadInput() {
        var input = new Input();

        try {
            var loader = Day1.class.getClassLoader().getResource("day1/input.txt");
            assert loader != null;

            var path = Paths.get(loader.toURI());
            try (var lines = Files.lines(path)) {
                lines.forEach(line -> {
                    var iterator = Arrays.stream(line.split("\\s+")).iterator();
                    input.left.add(Integer.parseInt(iterator.next()));
                    input.right.add(Integer.parseInt(iterator.next()));
                });
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static void part1() {
        var input = loadInput();
        var distances = new ArrayList<Integer>();

        Collections.sort(input.left);
        Collections.sort(input.right);

        assert input.left.size() == input.right.size();
        for (var i = 0; i < input.left.size(); i++) {
            distances.add(Math.abs(input.left.get(i) - input.right.get(i)));
        }

        var result = distances.stream().mapToInt(Integer::intValue).sum();

        System.out.println("part 1");
        System.out.println("left: " + input.left);
        System.out.println("right: " + input.right);
        System.out.println("distances: " + distances);
        System.out.println("result: " + result + "\n");
    }

    public static void part2() {
        var input = loadInput();
        var occurrences = new HashMap<Integer, Integer>();

        input.right.forEach(item -> occurrences.put(item, occurrences.getOrDefault(item, 0) + 1));
        var similarity = input.left.stream().map(item -> item * occurrences.getOrDefault(item, 0)).toList();

        var result =  similarity.stream().mapToInt(Integer::intValue).sum();

        System.out.println("part 2");
        System.out.println("occurrences: " + occurrences);
        System.out.println("similarity: " + similarity);
        System.out.println("result: " + result + "\n");
    }
}
