package com.qloak.qlibs.config.validation;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@FunctionalInterface
public interface Validator<T> extends Predicate<T> {
    boolean test(T value);

    default Validator<T> and(@NotNull Validator<T> other) {
        return v -> this.test(v) && other.test(v);
    }

    static <T> Validator<T> alwaysTrue() {
        return v -> true;
    }

    static Validator<Integer> range(int min, int max) {
        return v -> v >= min && v <= max;
    }

    static Validator<Double> range(double min, double max) {
        return v -> v >= min && v <= max;
    }

    static Validator<String> nonEmpty() {
        return v -> v != null && !v.isEmpty();
    }

    static Validator<String> matchesRegex(@NotNull String regex) {
        return v -> v != null && v.matches(regex);
    }
}
