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
class NearAssertTest {

    @Canvas(print = true)
    public AsciiCanvas canvas;

    @Nested
    class top {
        @Test
        @Print("""
            ╔╗
            ╚╝

             ╔═╗
             ╚═╝
            """)
        void success() {
            expect(canvas.rect(1, 2, 2, 3))
                .near().top()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .near().top().about(1).pixels()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .above().about(1).pixels()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .near().about(1).pixels()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .above().less(2).pixels()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .above().greater(0).pixels()
                .of(canvas.rect(2, 5, 4, 6));
            expect(canvas.rect(1, 2, 2, 3))
                .above().between(0).and(2).pixels()
                .of(canvas.rect(2, 5, 4, 6));
        }

        @Test
        @Print("""
            ╔═╗
            ║╔╬╗
            ╚╬╝║
             ║ ║
             ╚═╝
            """)
        void butWasOverlapping() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(1, 2, 3, 4))
                .near().top().about(1).pixels()
                .of(canvas.rect(2, 3, 4, 6)));
            assertThat(e).hasMessage("expected [1, 2] => [3, 4] and [2, 3] => [4, 6] to be near but were overlapping or skew.");
        }

    }

    @Nested
    class bottom {
        @Test
        @Print("""
            ╔═╗
            ║ ║
            ╚═╝

            ╔════╗
            ╚════╝
            """)
        void success() {
            expect(canvas.rect(1, 5, 6, 6))
                .near().bottom()
                .of(canvas.rect(1, 1, 3, 3));
            expect(canvas.rect(1, 5, 6, 6))
                .near().bottom().about(1).pixels()
                .of(canvas.rect(1, 1, 3, 3));
            expect(canvas.rect(1, 5, 6, 6))
                .below().about(1).pixels()
                .of(canvas.rect(1, 1, 3, 3));
            expect(canvas.rect(1, 5, 6, 6))
                .near().about(1).pixels()
                .of(canvas.rect(1, 1, 3, 3));
        }

        @Test
        @Print("""
            ╔═╗
            ║ ║
            ╚═╝

            ╔════╗
            ╚════╝
            """)
        void withPrecision() {
            expect(canvas.rect(1, 5, 6, 6))
                .near().withPrecision(1).pixels()
                .bottom().about(2).pixels()
                .of(canvas.rect(1, 1, 3, 3));
        }

        @Test
        @Print("""
            ╔═╗ ╔══╗
            ║ ║ ║  ║
            ╚═╝ ╚══╝
            """)
        void butWasSkew() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(5, 1, 8, 3))
                .near().bottom().about(1).pixels()
                .of(canvas.rect(1, 1, 3, 3)));
            assertThat(e).hasMessage("expected [5, 1] => [8, 3] and [1, 1] => [3, 3] to be near but were overlapping or skew.");
        }

    }

    @Nested
    class left {
        @Test
        @Print("""
            ╔╗ ╔╗
            ╚╝ ║║
               ║║
               ║║
               ╚╝
            """)
        void success() {
            expect(canvas.rect(1, 1, 2, 2))
                .near().left()
                .of(canvas.rect(4, 1, 5, 5));
            expect(canvas.rect(1, 1, 2, 2))
                .near().left().about(1).pixels()
                .of(canvas.rect(4, 1, 5, 5));
            expect(canvas.rect(1, 1, 2, 2))
                .left().about(1).pixels()
                .of(canvas.rect(4, 1, 5, 5));
            expect(canvas.rect(1, 1, 2, 2))
                .near().about(1).pixels()
                .of(canvas.rect(4, 1, 5, 5));
        }

        @Test
        @Print("""
            ╔╗ ╔╗
            ╚╝ ║║
               ║║
               ║║
               ╚╝
            """)
        void butWasRight() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(4, 1, 5, 5))
                .near().left()
                .of(canvas.rect(1, 1, 2, 2)));
            assertThat(e).hasMessage("expected [4, 1] => [5, 5] and [1, 1] => [2, 2] to be near but were overlapping or skew.");

        }

    }

    @Nested
    class right {
        @Test
        @Print("""
            ╔╗
            ╚╝ ╔═╗
               ║ ║
               ╚═╝
            """)
        void success() {
            expect(canvas.rect(4, 2, 6, 4))
                .near().right()
                .of(canvas.rect(1, 1, 2, 2));
            expect(canvas.rect(4, 2, 6, 4))
                .near().right().about(1).pixels()
                .of(canvas.rect(1, 1, 2, 2));
            expect(canvas.rect(4, 2, 6, 4))
                .right().about(1).pixels()
                .of(canvas.rect(1, 1, 2, 2));
            expect(canvas.rect(4, 2, 6, 4))
                .near().about(1).pixels()
                .of(canvas.rect(1, 1, 2, 2));
        }

        @Test
        @Print("""
            ╔╗
            ╚╝


                ╔═╗
                ║ ║
                ╚═╝
            """)
        void butWasToFar() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(5, 5, 7, 7))
                .near().right().about(1).pixels()
                .of(canvas.rect(1, 1, 2, 2)));
            assertThat(e).hasMessage("expected [5, 5] => [7, 7] to have distance of 1px to [1, 1] => [2, 2] at right but found 2px.");

        }

    }

}
