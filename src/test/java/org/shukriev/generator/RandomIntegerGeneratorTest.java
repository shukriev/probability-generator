package org.shukriev.generator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.shukriev.exception.GeneratorArgumentValidationException;
import org.shukriev.exception.GeneratorException;

import java.util.Arrays;
import java.util.HashMap;

public class RandomIntegerGeneratorTest {

	@Test
	@DisplayName("Successfully generated random numbers")
	public void testRandomIntegerGeneratorShouldReturnNumbersSuccessfully() {
		final var randomNumbers = new Integer[]{-1, 0, 1, 2, 3};
		final var probabilities = new float[]{0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
		final var generator = RandomIntegerGenerator.of(randomNumbers, probabilities);

		final var randomNumberOccurrenceCount = new HashMap<Integer, Integer>();

		final var numberOfRuns = 100;
		for (int i = 0; i < numberOfRuns; i++) {
			final var number = generator.next();
			final var occurrenceCount = randomNumberOccurrenceCount.getOrDefault(number, 0) + 1;
			randomNumberOccurrenceCount.put(number, occurrenceCount);
		}

		//1. Assert retrieved random numbers are matching to some of the provided numbers.
		//Having in mind that not all the numbers might be in the returned response
		Assertions.assertAll("Assert returned values are correct",
				() -> Assertions.assertTrue(
						Arrays.asList(randomNumbers).containsAll(randomNumberOccurrenceCount.keySet()))
		);


		//2. Assert Occurrence count(sum) is equal to the number of Runs
		Assertions.assertEquals(numberOfRuns, randomNumberOccurrenceCount.values().stream().mapToInt(Integer::intValue).sum());
	}


	@Test
	@DisplayName("Failure due to bad numbers or probability setup")
	public void testRandomIntegerGeneratorShouldReturnGeneratorValidationError() {
		final var randomNumbers = new Integer[]{-1, 0, 1, 2, 3, 5};
		final var probabilities = new float[]{0.01f, 0.3f, 0.58f, 0.1f, 0.01f};

		final var exception = Assertions.assertThrows(GeneratorArgumentValidationException.class, () -> {
			RandomIntegerGenerator.of(randomNumbers, probabilities);
		});

		Assertions.assertEquals("The length of random elements and probabilities arrays should be same", exception.getMessage());
	}

	@Test
	@DisplayName("Failure due to bad probability setup")
	public void testRandomIntegerGeneratorShouldReturnGeneratorException() {
		final var randomNumbers = new Integer[]{-1, 0, 1, 2, 3};
		final var probabilities = new float[]{-0.01f, -0.3f, -0.58f, -0.1f, -0.01f};
		final var generator = RandomIntegerGenerator.of(randomNumbers, probabilities);

		final var exception = Assertions.assertThrows(GeneratorException.class, generator::next);

		Assertions.assertEquals("Unable to generate a random number", exception.getMessage());
	}

	@Test
	@DisplayName("Failure due to provided null")
	public void testRandomIntegerGeneratorShouldReturnNumbersFailure() {
		final var randomNumbers = new Integer[]{null, 0, 1, 2, 3};
		final var probabilities = new float[]{0.01f, 0.3f, 0.58f, 0.1f, 0.01f};
		final var generator = RandomIntegerGenerator.of(randomNumbers, probabilities);

		final var randomNumberOccurrenceCount = new HashMap<Integer, Integer>();

		final var numberOfRuns = 100;
		for (int i = 0; i < numberOfRuns; i++) {
			final var number = generator.next();
			final var occurrenceCount = randomNumberOccurrenceCount.getOrDefault(number, 0) + 1;
			randomNumberOccurrenceCount.put(number, occurrenceCount);
		}

		//1. Assert retrieved random numbers are matching to some of the provided numbers.
		//Having in mind that not all the numbers might be in the returned response
		Assertions.assertAll("Assert returned values are correct",
				() -> Assertions.assertTrue(
						Arrays.asList(randomNumbers).containsAll(randomNumberOccurrenceCount.keySet()))
		);


		//2. Assert Occurrence count(sum) is equal to the number of Runs
		Assertions.assertEquals(numberOfRuns, randomNumberOccurrenceCount.values().stream().mapToInt(Integer::intValue).sum());
	}
}
