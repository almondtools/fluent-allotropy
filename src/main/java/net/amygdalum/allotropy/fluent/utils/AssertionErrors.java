package net.amygdalum.allotropy.fluent.utils;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

public final class AssertionErrors {

    private AssertionErrors() {
    }

    public static Builder expected(Object element) {
        return new Builder().expected(element);
    }

    @SafeVarargs
    public static <T> Builder expectedAllOf(T... elements) {
        return expectedAllOf(asList(elements));
    }

    public static <T> Builder expectedAllOf(List<T> elements) {
        return new Builder().expectedAllOf(elements);
    }

    public static class Builder {

        private StringBuilder builder;

        public Builder() {
            this.builder = new StringBuilder();
        }

        public <T> Builder expectedAllOf(List<T> objects) {
            builder.append(" expected all of [")
                .append(objects.stream()
                    .map(Object::toString)
                    .collect(joining(", ")))
                .append("]");
            return this;
        }

        public Builder expected(Object object) {
            builder.append(" expected ").append(object);
            return this;
        }

        public <T> Builder of(Object object) {
            builder.append(" of ").append(object);
            return this;
        }

        public <T> Builder of(List<T> objects) {
            builder.append(" of [")
                .append(objects.stream()
                    .map(Object::toString)
                    .collect(joining(", ")))
                .append("]");
            return this;
        }

        public Builder and(Object object) {
            builder.append(" and ").append(object);
            return this;
        }

        public Builder to(Object object) {
            builder.append(" to ").append(object);
            return this;
        }

        public Builder aligned(Object object) {
            builder.append(" aligned ").append(object);
            return this;
        }

        public Builder toBe(Object object) {
            builder.append(" to be ").append(object);
            return this;
        }

        public Builder toHave(Object object) {
            builder.append(" to have ").append(object);
            return this;
        }

        public Builder butWere(Object object) {
            builder.append(" but were ").append(object);
            return this;
        }

        public Builder butWas(Object object) {
            builder.append(" but was ").append(object);
            return this;
        }

        public Builder at(Object object) {
            builder.append(" at ").append(object);
            return this;
        }

        public Builder __(Object object) {
            builder.append(" ").append(object);
            return this;
        }

        public Builder butFailed() {
            builder.append(" but failed");
            return this;
        }

        public Builder butFound(Object object) {
            builder.append(" but found ").append(object);
            return this;
        }

        public Builder butNot(Object object) {
            builder.append(" but not ").append(object);
            return this;
        }

        public Builder butNotAt(Object object) {
            builder.append(" but not at ").append(object);
            return this;
        }

        public Builder insideOf(VisualElement object) {
            builder.append(" inside of ").append(object);
            return this;
        }

        public Builder startingWith(Object object) {
            builder.append(" starting with ").append(object);
            return this;
        }

        public Builder endingWith(Object object) {
            builder.append(" ending with ").append(object);
            return this;
        }

        public Builder containing(Object object) {
            builder.append(" containing ").append(object);
            return this;
        }

        public Builder overlapping(Object object) {
            builder.append(" containing ").append(object);
            return this;
        }

        public Builder with(Object object) {
            builder.append(" with ").append(object);
            return this;
        }

        public String message() {
            return builder.append(".").toString().trim();
        }

        public AssertionError asAssertionError() {
            return new AssertionError(message());
        }

    }
}
