package org.shukriev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shukriev.generator.RandomIntegerGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Task:
 * Create a random generator class which takes as input a seed of numbers and their
 * associated probabilities. Implement the method nextNum() and a minimal but effective set of
 * unit tests. The method nextNum() should only ever return one of the seeded numbers and
 * given enough calls the probability of the output should converge on the seed probability.
 * Implement the solution in the language of your choice, Java is preferred, but Kotlin and other
 * languages are completely fine. Make sure your code is exemplary, as if it was going to be
 * shipped as part of a production system. You should leverage comments whenever you see fit to
 * Algorithm:
 * As a quick check, given Random Numbers are [-1, 0, 1, 2, 3] and Probabilities are
 * [0.01, 0.3, 0.58, 0.1, 0.01] if we call nextNum() 100 times we may get the following results.
 * As the results are random, these particular results are unlikely.
 * -1: 1 times
 * 0: 22 times
 * 1: 57 times
 * 2: 20 times
 * 3: 0 times
 */
public class Main {
	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		final var randomNumbers = new Integer[]{-1, 0, 1, 2, 3};
		final var probabilities = new float[]{0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
		final var numberOfRuns = 100;

		final var generator = RandomIntegerGenerator.of(randomNumbers, probabilities);
		final var randomNumberOccurrenceCount = new HashMap<Integer, Integer>();

		IntStream.range(0, numberOfRuns).forEach(x -> collectOccurrences(randomNumberOccurrenceCount, generator.next()));

		printOccurrenceMap(randomNumberOccurrenceCount);
	}

	private static void collectOccurrences(final Map<Integer, Integer> occurrenceMap, final Integer number) {
		final var occurrenceCount = occurrenceMap.getOrDefault(number, 0) + 1;
		occurrenceMap.put(number, occurrenceCount);
	}

	private static void printOccurrenceMap(final Map<Integer, Integer> occurrenceMap) {
		occurrenceMap.forEach((number, occurrenceCount) -> {
			LOGGER.info("{} : {} times", number, occurrenceCount);
		});
	}
}