package carlossilva.dockermanager.core.util.result;

public final class Success<T, E extends Exception> extends Result<T, E> {
    public Success(T value) {
        super(value);
    }

    public T getValue() {
        return value;
    }
}
