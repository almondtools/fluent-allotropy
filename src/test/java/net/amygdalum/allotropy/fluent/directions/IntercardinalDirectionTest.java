package net.amygdalum.allotropy.fluent.directions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IntercardinalDirectionTest {

    @Test
     void testLabel() {
        assertThat(IntercardinalDirection.NE.label()).isEqualTo("top right");
        assertThat(IntercardinalDirection.NW.label()).isEqualTo("top left");
        assertThat(IntercardinalDirection.SE.label()).isEqualTo("bottom right");
        assertThat(IntercardinalDirection.SW.label()).isEqualTo("bottom left");
    }

}
