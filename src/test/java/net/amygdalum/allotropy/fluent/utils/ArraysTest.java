package net.amygdalum.allotropy.fluent.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArraysTest {

    @Nested
    class testToArray {
        @Test
        void ordinary() {
            assertThat(Stream.of("a", "b")
                .collect(Arrays.toArray())).contains("a", "b");
        }

        @Test
        void mixedTypeArray() {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Stream.of(new A(), new B())
                .collect(Arrays.toArray()));
            assertThat(e).hasMessage("expecting all elements of same type, but found multiple types");
        }

    }

    @Nested
    class testConcurrentToArray {
        @Test
        void ordinary() {
            assertThat(Stream.of("a", "b", "c", "d")
                .parallel()
                .collect(Arrays.toArray())).contains("a", "b", "c", "d");
        }

        @Test
        void mixedTypeArray() {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Stream.of(new A(), new A(), new A(), new B())
                .parallel()
                .collect(Arrays.toArray()));
            assertThat(e.getMessage()).isEqualTo("expecting all elements of same type, but found multiple types");
        }

    }

    private static class A {

    }

    private static class B extends A {

    }
}
