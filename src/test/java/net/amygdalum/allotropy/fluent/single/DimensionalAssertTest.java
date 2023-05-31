package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.height;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.width;
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
class DimensionalAssertTest {

    @Canvas(print = true)
    public AsciiCanvas canvas;

    @Nested
    class width {
        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void equal() {
            expect(canvas.rect(2, 2, 3, 3))
                .width().equal(2).pixels();
            expect(canvas.rect(2, 2, 3, 3))
                .width().equal(20).percentOf(canvas);
            expect(canvas.rect(2, 2, 3, 3))
                .width().equal(20).percentOf(canvas, height());
            expect(canvas.rect(2, 2, 3, 3))
                .width().equal(20).percentOf(canvas, width());
            expect(canvas.rect(2, 2, 3, 3))
                .width().equal(100).percentOf(height());
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void notEqual() {
            expect(canvas.rect(2, 2, 3, 3))
                .width().greater(1).pixels();
            expect(canvas.rect(2, 2, 3, 3))
                .width().less(3).pixels();
            expect(canvas.rect(2, 2, 3, 3))
                .width().greater(10).percentOf(canvas, width());
            expect(canvas.rect(2, 2, 3, 3))
                .width().less(110).percentOf(height());
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void failingGreater() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .width().greater(30).percentOf(canvas));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] to have width > 30% of the width of [0, 0] => [9, 9] but found 2px.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void failingLess() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .width().less(10).percentOf(canvas));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] to have width < 10% of the width of [0, 0] => [9, 9] but found 2px.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void failingEqual() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .width().equal(3).pixels());
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] to have width of 3px but found 2px.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void failingGreaterWithPrecision() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .width()
                .withPrecision(1).pixels()
                .greater(35).percentOf(canvas));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] to have width > 35% of the width of [0, 0] => [9, 9] but found 2px.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝
            """)
        void failingRatio() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3))
                .width().equal(150).percentOf(height()));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 3] to have width of 150% of the height of [2, 2] => [3, 3] but found 2px.");
        }

    }

    @Nested
    class height {
        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void equal() {
            expect(canvas.rect(2, 2, 3, 4))
                .height().equal(3).pixels();
            expect(canvas.rect(2, 2, 3, 4))
                .height().equal(30).percentOf(canvas);
            expect(canvas.rect(2, 2, 3, 4))
                .height().equal(30).percentOf(canvas, height());
            expect(canvas.rect(2, 2, 3, 4))
                .height().equal(30).percentOf(canvas, width());
            expect(canvas.rect(2, 2, 3, 4))
                .height().equal(150).percentOf(width());
        }

        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void notEqual() {
            expect(canvas.rect(2, 2, 3, 4))
                .height().greater(2).pixels();
            expect(canvas.rect(2, 2, 3, 4))
                .height().less(4).pixels();
            expect(canvas.rect(2, 2, 3, 4))
                .height().greater(20).percentOf(canvas, height());
            expect(canvas.rect(2, 2, 3, 4))
                .height().less(200).percentOf(width());
        }

        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void failingGreater() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4))
                .height().greater(40).percentOf(canvas));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 4] to have height > 40% of the height of [0, 0] => [9, 9] but found 3px.");
        }

        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void failingLess() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4))
                .height().less(2).pixels());
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 4] to have height < 2px but found 3px.");
        }

        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void failingEqual() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4))
                .height().equal(20).percentOf(canvas));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 4] to have height of 20% of the height of [0, 0] => [9, 9] but found 3px.");
        }

        @Test
        @Print("""
            ╔╗
            ║║
            ╚╝
            """)
        void failingRatio() {
            AssertionError e = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4))
                .height().equal(100).percentOf(width()));
            assertThat(e.getMessage()).isEqualTo("expected [2, 2] => [3, 4] to have height of 100% of the width of [2, 2] => [3, 4] but found 3px.");
        }
    }
}
