package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint.lt;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class LtDistanceConstraintTest {

    @Test
    void testTest() {
        LtDistanceConstraint constraint = lt(new PixelDistance(2.0));

        assertThat(constraint.test(new PixelDistance(1.8))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(false);
    }

    @Test
    void testWithPrecision() {
        LtDistanceConstraint constraint = lt(new PixelDistance(2.0)).withPrecision(new PixelPrecision(1));

        assertThat(constraint.test(new PixelDistance(1.8))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.9))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.0))).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(lt(new PixelDistance(2.0)).description()).isEqualTo("< 2px");
        assertThat(lt(new PixelDistance(2.3)).description()).isEqualTo("< 2.3px");
    }

}
