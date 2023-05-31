package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.height;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.width;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.Print;

@ExtendWith(CanvasExtension.class)
class EachTest {

    @Canvas(print = true)
    private AsciiCanvas canvas;

    @Test
    @Print("""
        ╔╗
        ╚╝

        ╔╗
        ╚╝

        ╔╗
        ╚╝
        """)
    void success() {
        expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
            .each(e -> e
                .width().equal(2).pixels()
                .and()
                .height().equal(2).pixels())
            .and()
            .each(e -> e
                .width().equal(100).percentOf(height()));
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
            .each(e -> e
                .width().equal(100).percentOf(width()))
            .and()
            .each(e -> e
                .width().equal(2).pixels()
                .and()
                .height().equal(2).pixels()));
        assertThat(error.getMessage()).isEqualTo("expected [2, 3] => [3, 6] to have height of 2px but found 4px.");
    }
}
