package net.amygdalum.allotropy.fluent.distances;

public class MissingContextException extends RuntimeException {

    public MissingContextException(Class<?> clazz) {
        super("no context information on class " + clazz.getSimpleName());
    }

}
