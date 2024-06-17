package net.amygdalum.allotropy.fluent.chunks;

import static net.amygdalum.allotropy.fluent.chunks.Chunks.sized;
import static net.amygdalum.allotropy.fluent.chunks.Chunks.window;
import static net.amygdalum.allotropy.fluent.elements.VisualElements.visualElement;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

class ChunksTest {

    @Nested
    class testWindow {
        @Test
        void noWindow() {
            VisualElement[] base = elements("item1", "item2");
            VisualElement[][] windowed = window(3).apply(base);

            assertThat(windowed).isEmpty();
        }

        @Test
        void oneWindow() {
            VisualElement[] base = elements("item1", "item2", "item3");
            VisualElement[][] windowed = window(3).apply(base);

            assertThat(windowed).hasDimensions(1, 3);
        }

        @Test
        void multipleWindows() {
            VisualElement[] base = elements("item1", "item2", "item3", "item4", "item5");
            VisualElement[][] windowed = window(3).apply(base);

            assertThat(windowed).hasDimensions(3, 3);
            assertThat(windowed[0]).isEqualTo(new VisualElement[] {base[0], base[1], base[2]});
            assertThat(windowed[1]).isEqualTo(new VisualElement[] {base[1], base[2], base[3]});
            assertThat(windowed[2]).isEqualTo(new VisualElement[] {base[2], base[3], base[4]});
        }

    }

    @Nested
    class testSized {
        @Test
        void noChunk() {
            VisualElement[] base = elements();
            VisualElement[][] chunked = sized(2).apply(base);

            assertThat(chunked).isEmpty();
        }

        @Test
        void oneChunk() {
            VisualElement[] base = elements("item1", "item2");
            VisualElement[][] chunked = sized(2).apply(base);

            assertThat(chunked).hasDimensions(1, 2);
        }

        @Test
        void oneAndPartialChunk() {
            VisualElement[] base = elements("item1", "item2", "item3");
            VisualElement[][] chunked = sized(2).apply(base);

            assertThat(chunked).hasNumberOfRows(2);
            assertThat(chunked[0]).isEqualTo(new VisualElement[] {base[0], base[1]});
            assertThat(chunked[1]).isEqualTo(new VisualElement[] {base[2]});
        }

        @Test
        void multipleChunks() {
            VisualElement[] base = elements("item1", "item2", "item3", "item4");
            VisualElement[][] chunked = sized(2).apply(base);

            assertThat(chunked).hasDimensions(2, 2);
            assertThat(chunked[0]).isEqualTo(new VisualElement[] {base[0], base[1]});
            assertThat(chunked[1]).isEqualTo(new VisualElement[] {base[2], base[3]});
        }
    }

    private VisualElement[] elements(String... names) {
        return Arrays.stream(names)
            .map(n -> (VisualElement) visualElement().withText(n))
            .toArray(VisualElement[]::new);
    }

}
