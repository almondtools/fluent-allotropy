package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.chunks.Chunks.window;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.Print;

@ExtendWith(CanvasExtension.class)
class ChunkedTest {

    @Canvas(print = true, height = 15)
    private AsciiCanvas canvas;

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
            .chunked(window(2), e -> e
                .alignedVertically()
                .withEachOther())
            .and()
            .each(e -> e.height().equal(2).pixels());
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
            .chunked(window(2), e -> e
                .alignedHorizontally()
                .top()
                .withEachOther()));
        assertThat(error.getMessage())
            .isEqualTo("expected [5, 4] => [6, 5] aligned horizontally at top with [2, 3] => [3, 6] but not at top.");
    }
}
