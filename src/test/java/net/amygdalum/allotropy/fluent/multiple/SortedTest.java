package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.Print;

@ExtendWith(CanvasExtension.class)
class SortedTest {

    @Canvas(print = true)
    private AsciiCanvas canvas;

    @Test
    @Print("""
              ╔╗
        ╔╗    ║║
        ║║ ╔╗ ║║
        ║║ ╚╝ ║║
        ╚╝    ║║
              ╚╝
        """)
    void success() {
        expect(canvas.rect(5, 4, 6, 5), canvas.rect(8, 2, 9, 7), canvas.rect(2, 3, 3, 6))
            .sorted((e1, e2) -> e1.bounds().left() - e2.bounds().left())
            .alignedHorizontally()
            .centered()
            .equallyDistanced()
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
        AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(5, 4, 6, 5), canvas.rect(8, 2, 9, 7), canvas.rect(2, 3, 3, 6))
            .alignedHorizontally()
            .centered()
            .equallyDistanced()
            .withEachOther());
        assertThat(error.getMessage()).isEqualTo("expected all of [[5, 4] => [6, 5], [8, 2] => [9, 7], [2, 3] => [3, 6]] aligned horizontally with each other equally distanced but found between -8px and 1px.");
    }
}
