package com.radionov.rxkata.fibonacci;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class FibonacciExercises {

    public Observable<Integer> fibonacci(int n) {
        return Observable
                .just(0)
                .repeat()
                .scan(new Integer[]{1, 0}, (r, start) -> new Integer[]{r[1], r[0] + r[1]})
                .map(r -> r[1])
                .take(n);
    }

    //Вариант через список
    public Observable<Integer> fibonacciWithList(int n) {

        List<Integer> sequence = new ArrayList<>();

        sequence.add(0);
        sequence.add(1);

        for (int i = 2; i < n; i++) {
            sequence.add(sequence.get(i - 1) + sequence.get(i - 2));
        }

        return Observable.fromIterable(sequence).take(n);
    }
}
