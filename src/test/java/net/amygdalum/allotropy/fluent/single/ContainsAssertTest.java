package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.Print;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

@ExtendWith(CanvasExtension.class)
class ContainsAssertTest {

    @Canvas(print = true)
    public AsciiCanvas canvas;

    @Nested
    class unconstrained {
        @Test
        @Print("""
            ╔═════╗
            ║╔╗   ║
            ║╚╝   ║
            ║  ╔═╗║
            ║  ╚═╝║
            ╚═════╝
            """)
        void success() {
            expect(canvas.rect(1, 1, 7, 6))
                .contains(canvas.rect(2, 2, 3, 3), canvas.rect(4, 4, 6, 5));
            expect(canvas.rect(1, 1, 7, 6))
                .contains(i -> new VisualElement[] {canvas.rect(2, 2, 3, 3), canvas.rect(4, 4, 6, 5)});
            expect(canvas.rect(1, 1, 7, 6))
                .contains(canvas.rect(2, 2, 3, 3))
                .and()
                .contains(canvas.rect(4, 4, 6, 5));
        }

        @Test
        @Print("""
            ╔══╗
            ║╔═╬═╗
            ║╚═╬═╝
            ║  ║
            ║  ║
            ╚══╝
            """)
        void butOverlapping() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 4, 6))
                .contains(canvas.rect(2, 2, 6, 3)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [4, 6] containing [2, 2] => [6, 3] but was overlapping.");
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void butContaining() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .contains(canvas.rect(1, 1, 4, 6)));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] containing [1, 1] => [4, 6] but was contained.");
        }

        @Test
        @Print("""
             ╔╗
             ╚╝

            ╔══╗
            ║  ║
            ╚══╝
            """)
        void butOutside() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .contains(canvas.rect(1, 5, 4, 7)));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] containing [1, 5] => [4, 7] but was outside.");
        }

    }

    @Nested
    class mixedConstraints {
        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void success() {
            expect(canvas.rect(1, 1, 4, 6))
                .contains()
                .less(2).pixels().top()
                .about(1).pixels().left()
                .greater(0).pixels().right()
                .between(2).and(4).pixels().bottom()
                .items(canvas.rect(2, 2, 3, 3));
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void withCommonPrecision() {
            expect(canvas.rect(1, 1, 4, 6))
                .contains().withPrecision(1).pixels()
                .less(1).pixels().top().withPrecision(2).pixels()
                .about(2).pixels().left()
                .greater(1).pixels().right()
                .between(2).and(4).pixels().bottom()
                .items(canvas.rect(2, 2, 3, 3));
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void failingInPrecision() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 4, 6))
                .contains()
                .less(1).pixels().top().withPrecision(1).pixels()
                .about(2).pixels().left().withPrecision(0).pixels()
                .greater(1).pixels().right()
                .between(2).and(4).pixels().bottom().withPrecision(2).pixels()
                .items(canvas.rect(2, 2, 3, 3)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [4, 6] containing [2, 2] => [3, 3] with distance "
                + "< 1px top, "
                + "of 2px left, "
                + "> 1px right, "
                + "between 2px and 4px bottom "
                + "but was "
                + "1px right, "
                + "1px left.");
        }
    }

    @Nested
    class allConstraints {
        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void success() {
            expect(canvas.rect(1, 1, 4, 6))
                .contains()
                .about(1).pixels().top()
                .about(1).pixels().left()
                .about(1).pixels().right()
                .about(3).pixels().bottom()
                .items(canvas.rect(2, 2, 3, 3));
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void oneDistanceToLarge() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 4, 6))
                .contains()
                .about(2).pixels().top()
                .about(1).pixels().left()
                .about(1).pixels().right()
                .about(3).pixels().bottom()
                .items(canvas.rect(2, 2, 3, 3)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [4, 6] containing [2, 2] => [3, 3] with distance "
                + "of 2px top, "
                + "of 1px left, "
                + "of 1px right, "
                + "of 3px bottom "
                + "but was "
                + "1px top.");
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
            """)
        void oneDistanceToLargeOneToSmall() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 4, 6))
                .contains()
                .about(1).pixels().top()
                .about(1).pixels().left()
                .about(2).pixels().right()
                .about(2).pixels().bottom()
                .items(canvas.rect(2, 2, 3, 3)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [4, 6] containing [2, 2] => [3, 3] with distance "
                + "of 1px top, "
                + "of 1px left, "
                + "of 2px right, "
                + "of 2px bottom "
                + "but was "
                + "1px right, "
                + "3px bottom.");
        }
    }
}
