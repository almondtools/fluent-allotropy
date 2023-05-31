package net.amygdalum.allotropy.fluent.elements;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void testDistanceTo() {
        assertThat(new Point(1,1).distanceTo(new Point(4,5))).isEqualTo(5);
    }

}
