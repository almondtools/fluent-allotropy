package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.DistanceConstraint.NONE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class DistanceConstraintTest {

    @Test
    void testTest() {
        assertThat(NONE.test(new PixelDistance(10000))).isEqualTo(true);
        assertThat(NONE.test(new PixelDistance(0))).isEqualTo(true);
        assertThat(NONE.test(new PixelDistance(-10000))).isEqualTo(true);
    }

    @Test
    void testWithPrecision() {
        DistanceConstraint alsoNone = NONE.withPrecision(new PixelPrecision(0));
        assertThat(alsoNone.test(new PixelDistance(10000))).isEqualTo(true);
        assertThat(alsoNone.test(new PixelDistance(0))).isEqualTo(true);
        assertThat(alsoNone.test(new PixelDistance(-10000))).isEqualTo(true);
    }

    @Test
    void testDescription() {
        assertThat(NONE.description()).isEqualTo("any");
    }

}
