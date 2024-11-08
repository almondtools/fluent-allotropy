package net.amygdalum.allotropy.fluent.distances;

public class AmbiguousContextException extends RuntimeException {

    public AmbiguousContextException(Class<?> clazz) {
        super("more than one information on class " + clazz.getSimpleName());
    }

}
