package com.radionov.rxkata.fibonacci;

import org.junit.Before;
import org.junit.Test;

public class FibonacciExercisesTest {

	private FibonacciExercises exercises;

	@Before
	public void setUp() throws Exception {
		exercises = new FibonacciExercises();
	}

	@Test
	public void fibonacciTest() throws Exception {
		exercises.fibonacci(10).test().assertResult(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
	}
}