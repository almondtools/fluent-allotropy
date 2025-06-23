package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.multiple.Selecting.by;
import static net.amygdalum.allotropy.fluent.multiple.Selecting.sized;
import static net.amygdalum.allotropy.fluent.multiple.Selecting.window;
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
class SelectingTest {

    @Canvas(print = true, height = 15, width = 15)
    private AsciiCanvas canvas;

    @Nested
    class window {
        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔╗
            ╚╝

            ╔╗
            ╚╝

            ╔╗
            ╚╝
            """)
        void success() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9), canvas.rect(2, 11, 3, 12))
                .selecting(window(2))
                .each(e -> e.elements()
                    .alignedVertically()
                    .withEachOther())
                .and()
                .each(e -> e.height().equal(5).pixels())
                .and()
                .alignedVertically()
                .withEachOther();
        }

        @Test
        @Print("""
                  ╔╗
            ╔╗    ║║
            ║║ ╔╗ ║║
            ║║ ╚╝ ║║
            ╚╝    ║║
                  ╚╝
            """)
        void failure() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(5, 4, 6, 5), canvas.rect(2, 3, 3, 6), canvas.rect(8, 2, 9, 7))
                .selecting(window(2)).each(e -> e.elements()
                    .alignedHorizontally()
                    .top()
                    .withEachOther()));
            assertThat(error.getMessage())
                .isEqualTo("expected [5, 4] => [6, 5] aligned horizontally at top with [2, 3] => [3, 6] but not at top.");
        }
    }

    @Nested
    class sized {
        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔╗
            ╚╝

            ╔╗
            ╚╝

            ╔╗
            ╚╝
            """)
        void success() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9), canvas.rect(2, 11, 3, 12))
                .selecting(sized(2))
                .count().equal(2)
                .and().each(e -> e.elements()
                    .alignedVertically()
                    .withEachOther())
                .and()
                .each(e -> e.height().equal(5).pixels())
                .and()
                .alignedVertically()
                .withEachOther();
        }

        @Test
        @Print("""
                  ╔╗
            ╔╗ ╔╗ ║║ ╔╗
            ║║ ║║ ║║ ║║
            ║║ ╚╝ ║║ ╚╝
            ╚╝    ║║
                  ╚╝
            """)
        void failure() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(
                canvas.rect(5, 3, 6, 5),
                canvas.rect(2, 3, 3, 6),
                canvas.rect(8, 2, 9, 7),
                canvas.rect(11, 3, 12, 5))
                    .selecting(sized(2)).each(e -> e.elements()
                        .alignedHorizontally()
                        .top()
                        .withEachOther()));
            assertThat(error.getMessage())
                .isEqualTo("expected [8, 2] => [9, 7] aligned horizontally at top with [11, 3] => [12, 5] but not at top.");
        }
    }

    @Nested
    class by {
        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔═╗
            ╚═╝

            ╔╗
            ╚╝

            ╔═╗
            ╚═╝
            """)
        void success() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 4, 6), canvas.rect(2, 8, 3, 9), canvas.rect(2, 11, 4, 12))
                .selecting(by(e -> e.bounds().width() > 2 ? null : e))
                .count().equal(2)
                .and()
                .each(e -> e.height().equal(2).pixels())
                .and()
                .alignedVertically()
                .withEachOther();
        }

        @Test
        @Print("""
                  ╔╗
            ╔╗ ╔╗ ║║ ╔╗
            ║║ ║║ ║║ ║║
            ║║ ╚╝ ║║ ╚╝
            ╚╝    ║║
                  ╚╝
            """)
        void failure() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(
                canvas.rect(5, 3, 6, 5),
                canvas.rect(2, 3, 3, 6),
                canvas.rect(8, 2, 9, 7),
                canvas.rect(11, 3, 12, 5))
                    .selecting(by(e -> e.bounds().height() < 3 ? null : e))
                    .alignedHorizontally()
                    .top()
                    .withEachOther());
            assertThat(error.getMessage())
                .isEqualTo("expected [5, 3] => [6, 5] aligned horizontally at top with [8, 2] => [9, 7] but not at top.");
        }
    }
}
