package carlossilva.dockermanager.core.util.result;

public final class Failure<T, E extends Exception> extends Result<T, E> {
    public Failure(E error) {
        super(error);
    }

    public E getError() {
        return error;
    }
}
