package carlossilva.dockermanager.core.util.result;

import java.util.function.Consumer;
import java.util.function.Function;

public sealed class Result<T, E extends Exception> permits Success, Failure {
    protected final T value;
    protected final E error;

    protected Result(T value) {
        this.value = value;
        this.error = null;
    }

    protected Result(E error) {
        this.value = null;
        this.error = error;
    }

    public static <T, E extends Exception> Success<T, E> success(T result) {
        return new Success<>(result);
    }

    public static <T, E extends Exception> Failure<T, E> failure(E error) {
        return new Failure<>(error);
    }

    public void fold(Consumer<E> onFailure, Consumer<T> onSuccess) {
        if (error != null) {
            onFailure.accept(error);
        } else {
            onSuccess.accept(value);
        }
    }

    public <R> R foldReturn(Function<E, R> onFailure, Function<T, R> onSuccess) {
        if (error != null) {
            return onFailure.apply(error);
        }
        return onSuccess.apply(value);
    }
}
