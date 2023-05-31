package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.EqDistanceConstraint.eq;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class EqDistanceConstraintTest {

    @Test
    void testTest() {
        EqDistanceConstraint constraint = eq(new PixelDistance(2.0));

        assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(false);
    }

    @Test
    void testWithPrecision() {
        EqDistanceConstraint constraint = eq(new PixelDistance(2.0)).withPrecision(new PixelPrecision(1));

        assertThat(constraint.test(new PixelDistance(0.9))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(1.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.1))).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(eq(new PixelDistance(2.0)).description()).isEqualTo("of 2px");
        assertThat(eq(new PixelDistance(2.3)).description()).isEqualTo("of 2.3px");
    }

}
