package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint.gt;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class GtDistanceConstraintTest {

    @Test
    void testTest() {
        GtDistanceConstraint constraint = gt(new PixelDistance(2.0));

        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.2))).isEqualTo(true);
    }

    @Test
    void testWithPrecision() {
        GtDistanceConstraint constraint = gt(new PixelDistance(2.0)).withPrecision(new PixelPrecision(1));

        assertThat(constraint.test(new PixelDistance(1.0))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(1.1))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.2))).isEqualTo(true);
    }

    @Test
    void testDescription() {
        assertThat(gt(new PixelDistance(2.0)).description()).isEqualTo("> 2px");
        assertThat(gt(new PixelDistance(2.3)).description()).isEqualTo("> 2.3px");
    }

}
