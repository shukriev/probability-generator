package org.shukriev.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shukriev.exception.GeneratorException;

/**
 * RandomIntegerGenerator is an implementation of Generator class with subtype of Integer
 * The class accepts array of random numbers and array of probabilities.
 * The class exposes single method nextNum() which returns a random number based on the provided details
 */
public final class RandomIntegerGenerator extends Generator<Integer> {
	private static final Logger LOGGER = LogManager.getLogger(RandomIntegerGenerator.class);

	private RandomIntegerGenerator(Integer[] randomNumbers, float[] probabilities) {
		super(randomNumbers, probabilities);
	}

	/**
	 * Static Factory Method to construct RandomIntegerGenerator
	 * @param randomNumbers - Array of numbers to configure the generator
	 * @param probabilities - Array of probabilities to configure the generator
	 * @return - Returns a class of RandomIntegerGenerator
	 */
	public static RandomIntegerGenerator of(Integer[] randomNumbers, float[] probabilities) {
		return new RandomIntegerGenerator(randomNumbers, probabilities);
	}

	/***
	 * Returns one of the randomNumbers. When this method is called multiple times over a long period, it should return the
	 * numbers roughly with the initialized probabilities.
	 * @return - Returned value is type of integer matching on the probability of the provided input arrays
	 * 		   - In worst case scenario the method can throw GeneratorException if the probabilities are not defined properly
	 */
	@Override
	public Integer next() {
		//1. Generate the random float number between 0 - 1
		final var randomFloat = getRandom().nextFloat();
		LOGGER.debug("Random Selected Number: {}", randomFloat);
		var cumulativeProbability = 0.0f;

		//2. Iterate the probabilities
		for (int i = 0; i < getProbabilities().length; i++) {
			//3. Sum a cumulative probability to match the probability of the numbers
			cumulativeProbability += getProbability(i);
			LOGGER.debug("Cumulative Probability: {}", cumulativeProbability);

			//4. Compare the random float coefficient to cumulative probability.
			//Given that the randomFloat number is less than the cumulative probability then we are at the right
			//probability of the given number based on the input array definitions
			if (randomFloat < cumulativeProbability) {
				final var number = getRandomElements(i);
				LOGGER.debug("Returned Random Number: {}", number);
				return number;
			}
		}

		// This should never happen if the probabilities are properly defined
		throw new GeneratorException("Unable to generate a random number");
	}
}