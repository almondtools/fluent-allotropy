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

@ExtendWith(CanvasExtension.class)
class OverlapsAssertTest {

    @Canvas(print = true)
    public AsciiCanvas canvas;

    @Nested
    class unconstrained {
        @Test
        @Print("""
            ╔══╗
            ║╔═╬═╗
            ║╚═╬═╝
            ║  ║
            ║  ║
            ╚══╝
            """)
        void horizontal() {
            expect(canvas.rect(2, 2, 6, 3))
                .overlaps()
                .with(canvas.rect(1, 1, 4, 6));
        }

        @Test
        @Print("""
            ╔════╗
            ║╔╗  ║
            ║║║  ║
            ╚╬╬══╝
             ║║
             ╚╝
            """)
        void vertical() {
            expect(canvas.rect(2, 2, 3, 6))
                .overlaps()
                .with(canvas.rect(1, 1, 6, 4));
        }

        @Test
        @Print("""
             ╔══╗
             ║  ║
             ║  ║
            ╔╬╗ ║
            ║╚╬═╝
            ║ ║
            ╚═╝
            """)
        void corner() {
            expect(canvas.rect(1, 4, 3, 7))
                .overlaps()
                .with(canvas.rect(2, 1, 5, 5));
        }

        @Test
        @Print("""
             ╔╗
             ║║
             ║║
            ╔╬╬╗
            ╚╬╬╝
             ║║
             ╚╝
            """)
        void through() {
            expect(canvas.rect(1, 4, 4, 5))
                .overlaps()
                .with(canvas.rect(2, 1, 3, 7));
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
        void butInside() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .overlaps()
                .with(canvas.rect(1, 1, 4, 6)));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] containing [1, 1] => [4, 6] but was inside.");
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
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 4, 6))
                .overlaps()
                .with(canvas.rect(2, 2, 3, 3)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [4, 6] containing [2, 2] => [3, 3] but was containing.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝  ╔╗
                ║║
                ╚╝
            """)
        void butOutside() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 1, 2, 2))
                .overlaps()
                .with(canvas.rect(5, 2, 6, 4)));
            assertThat(e.getMessage()).isEqualTo("expected [1, 1] => [2, 2] containing [5, 2] => [6, 4] but was outside.");
        }
    }

    @Nested
    class mixedConstraints {
        @Test
        @Print("""
            ╔══╗
            ║╔═╬═╗
            ║╚═╬═╝
            ║  ║
            ║  ║
            ╚══╝
            """)
        void left() {
            expect(canvas.rect(2, 2, 6, 3))
                .overlaps()
                .about(3).pixels().left()
                .with(canvas.rect(1, 1, 4, 6));
            expect(canvas.rect(1, 1, 4, 6))
                .overlaps()
                .greater(2).pixels().right()
                .with(canvas.rect(2, 2, 6, 3));
            expect(canvas.rect(1, 1, 4, 6))
                .overlaps()
                .less(4).pixels().right()
                .with(canvas.rect(2, 2, 6, 3));
        }

        @Test
        @Print("""
             ╔══╗
             ║  ║
             ║  ║
            ╔╬╗ ║
            ║╚╬═╝
            ║ ║
            ╚═╝
            """)
        void topRight() {
            expect(canvas.rect(1, 4, 3, 7))
                .overlaps()
                .between(1).and(2).pixels().top()
                .between(2).and(3).pixels().right()
                .with(canvas.rect(2, 1, 5, 5));
            expect(canvas.rect(2, 1, 5, 5))
                .overlaps()
                .less(3).pixels().bottom()
                .less(3).pixels().left()
                .with(canvas.rect(1, 4, 3, 7));
        }

        @Test
        @Print("""
            ╔═╗
            ║ ║
            ║ ║
            ║╔╬═╗
            ╚╬╝ ║
             ║  ║
             ╚══╝
             """)
        void topLeft() {
            expect(canvas.rect(2, 4, 5, 7))
                .overlaps()
                .greater(1).pixels().top()
                .greater(1).pixels().left()
                .with(canvas.rect(1, 1, 3, 5));
            expect(canvas.rect(1, 1, 3, 5))
                .overlaps()
                .about(2).pixels().bottom()
                .about(2).pixels().right()
                .with(canvas.rect(2, 4, 5, 7));
        }

        @Test
        @Print("""
             ╔╗
             ║║
             ║║
            ╔╬╬╗
            ╚╬╬╝
             ║║
             ╚╝
            """)
        void through() {
            expect(canvas.rect(1, 4, 4, 5))
                .overlaps()
                .about(3).pixels().right()
                .about(3).pixels().left()
                .with(canvas.rect(2, 1, 3, 7));
            expect(canvas.rect(2, 1, 3, 7))
                .overlaps()
                .greater(3).pixels().top()
                .less(5).pixels().bottom()
                .with(canvas.rect(1, 4, 4, 5));
        }

        @Test
        @Print("""
            ╔══╗
            ║  ╠═╗
            ║  ╠═╝
            ║  ║
            ║  ║
            ╚══╝
             """)
        void withRelaxedPrecision() {
            expect(canvas.rect(4, 2, 6, 3))
                .overlaps()
                .withPrecision(1).pixels()
                .about(2).pixels().left()
                .with(canvas.rect(1, 1, 4, 6));
            expect(canvas.rect(4, 2, 6, 3))
                .overlaps()
                .about(2).pixels().left()
                .withPrecision(1).pixels()
                .with(canvas.rect(1, 1, 4, 6));
        }

        @Test
        @Print("""
            ╔═╗
            ║ ║
            ║ ║
            ║╔╬═╗
            ╚╬╝ ║
             ║  ║
             ╚══╝
             """)
        void butMissedConstraint() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 4, 5, 7))
                .overlaps()
                .greater(2).pixels().top()
                .greater(3).pixels().left()
                .with(canvas.rect(1, 1, 3, 5)));
            assertThat(e.getMessage()).isEqualTo("expected [2, 4] => [5, 7] containing [1, 1] => [3, 5] with distance "
                + "> 2px top, "
                + "> 3px left "
                + "but was "
                + "2px top, "
                + "2px left.");
        }

    }
}
