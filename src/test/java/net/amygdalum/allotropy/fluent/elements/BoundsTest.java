package net.amygdalum.allotropy.fluent.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoundsTest {

    @Nested
    class testFrom {
        @Test
        public void viewport() {
            Bounds orig = Bounds.from(Map.of("x","50","y","20","width", "100", "height", "100"));
            Bounds viewport = Bounds.from(orig);

            assertThat(viewport).isEqualTo(orig);
        }

        @Test
        public void incompleteMap() {
            assertThrows(IllegalArgumentException.class, () -> Bounds.from(Map.of("width", "100","height","100")));
        }

        @Test
        public void invalidSource() {
            assertThrows(IllegalArgumentException.class, () -> Bounds.from(new Object()));
        }
    }


}
