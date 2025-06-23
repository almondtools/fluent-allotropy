package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.VisualElements.visualElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChunkElementTest {

    @Nested
    class testParent {
        @Test
        void commonParent() {
            var parent = visualElement();
            var e1 = visualElement().withParent(parent);
            var e2 = visualElement().withParent(parent);

            var chunk = new ChunkElement<>(new VisualElement[] {e1, e2});

            assertThat(chunk.parent()).hasValue(parent);
        }

        @Test
        void commonAncestor() {
            var parent = visualElement();
            var subparent = visualElement().withParent(parent);
            var e1 = visualElement().withParent(parent);
            var e2 = visualElement().withParent(subparent);

            var chunk = new ChunkElement<>(new VisualElement[] {e1, e2});

            assertThat(chunk.parent()).hasValue(parent);
        }
    }

    @Test
    void testBounds() {
        var e1 = visualElement().withBounds(new Bounds(2, 2, 4, 4));
        var e2 = visualElement().withBounds(new Bounds(3, 3, 5, 5));
        var chunk = new ChunkElement<>(new VisualElement[] {e1, e2});

        assertThat(chunk.bounds()).isEqualTo(new Bounds(2, 2, 5, 5));
    }

    @Test
    void testToString() {
        var e1 = visualElement().withText("e1");
        var e2 = visualElement().withText("e2");
        var chunk = new ChunkElement<>(new VisualElement[] {e1, e2});

        assertThat(chunk.toString()).isEqualTo("[[e1], [e2]]");
    }

}
