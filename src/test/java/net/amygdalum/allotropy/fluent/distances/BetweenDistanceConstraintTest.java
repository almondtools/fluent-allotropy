package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.distances.BetweenDistanceConstraint.between;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class BetweenDistanceConstraintTest {

    @Test
    void testTest() {
        Predicate<Distance> constraint = between(new PixelDistance(2.0), new PixelDistance(3.0))
            .inContext(ctx());

        assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.5))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.1))).isEqualTo(false);
    }

    @Test
    void testWithPrecision() {
        Predicate<Distance> constraint = between(new PixelDistance(2.0), new PixelDistance(3.0)).withPrecision(new PixelPrecision(1))
            .inContext(ctx());

        assertThat(constraint.test(new PixelDistance(0.9))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(1.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.5))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(4.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(4.1))).isEqualTo(false);
    }

    @Test
    void testDescribeIn() {
        assertThat(between(new PixelDistance(2.0), new PixelDistance(3.0)).describeIn(ctx())).isEqualTo("between 2px and 3px");
        assertThat(between(new PixelDistance(2.5), new PixelDistance(3.2)).describeIn(ctx())).isEqualTo("between 2.5px and 3.2px");
    }

}
