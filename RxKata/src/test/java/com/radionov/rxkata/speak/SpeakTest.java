package com.radionov.rxkata.speak;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

public class SpeakTest {

    private SpeakExercises exercises;

    @Before
    public void setUp() throws Exception {
        exercises = new SpeakExercises();
    }

    @Test
    public void speakExercise() throws Exception {
        Observable<String> alice = exercises.speak(
                "A, B C", 110);
        Observable<String> bob = exercises.speak(
                "A B, C", 90);
        Observable<String> jane = exercises.speak(
                "A B C", 100);

        /**
         * Править здесь
         */
        TestObserver<String> test = Observable
                .concat(
                        alice.map(w -> "Alice: " + w),
                        bob.map(w -> "Bob:   " + w),
                        jane.map(w -> "Jane:  " + w)
                ).test();

        test.awaitTerminalEvent();
        test.assertResult(
                "Alice: A",
                "Alice: B",
                "Alice: C",
                "Bob:   A",
                "Bob:   B",
                "Bob:   C",
                "Jane:  A",
                "Jane:  B",
                "Jane:  C"
        ).awaitTerminalEvent();
    }
}