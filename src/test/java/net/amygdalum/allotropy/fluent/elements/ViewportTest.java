package net.amygdalum.allotropy.fluent.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ViewportTest {

    @Test
    void testParent() {
        Viewport viewport = Viewport.from(Map.of("width", "100", "height", "100"));

        assertThat(viewport.parent()).isEmpty();
    }

    @Test
    public void testBounds() {
        Viewport viewport = Viewport.from(Map.of("width", "100", "height", "100"));

        assertThat(viewport.bounds()).isEqualTo(new Bounds(0, 0, 100, 100));
    }

    @Nested
    class testFrom {
        @Test
        public void viewport() {
            Viewport orig = Viewport.from(Map.of("width", "100", "height", "100"));
            Viewport viewport = Viewport.from(orig);

            assertThat(viewport).isEqualTo(orig);
        }

        @Test
        public void incompleteMap() {
            assertThrows(IllegalArgumentException.class, () -> Viewport.from(Map.of("width", "100")));
        }

        @Test
        public void invalidSource() {
            assertThrows(IllegalArgumentException.class, () -> Viewport.from(new Object()));
        }
    }

}
