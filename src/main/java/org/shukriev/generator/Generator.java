package org.shukriev.generator;

import org.shukriev.exception.GeneratorArgumentValidationException;

import java.util.Random;

/**
 * Generator abstraction allowing creating of generator of different types.
 * By extending the generator the implementer has to provide the type of the generator and implement the abstract method
 * next()
 *
 * @param <T> - Generator Type
 */
public abstract sealed class Generator<T> permits RandomIntegerGenerator {
	private final T[] randomElements;
	private final float[] probabilities;
	private final Random random;

	protected Generator(T[] randomElements, float[] probabilities) {
		//Assert that randomNumbers = probabilities
		if (randomElements.length != probabilities.length) {
			throw new GeneratorArgumentValidationException("The length of random elements and probabilities arrays should be same");
		}

		this.randomElements = randomElements;
		this.probabilities = probabilities;
		this.random = new Random();
	}

	public abstract T next();

	protected final T getRandomElements(int index) {
		return this.randomElements[index];
	}

	protected final float[] getProbabilities() {
		return this.probabilities;
	}

	protected final float getProbability(int index) {
		return this.probabilities[index];
	}

	protected final Random getRandom() {
		return random;
	}
}
