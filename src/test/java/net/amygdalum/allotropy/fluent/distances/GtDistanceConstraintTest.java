package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint.gt;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class GtDistanceConstraintTest {

    @Nested
    class testTest {
        @Test
        void pixels() {
            Predicate<Distance> constraint = gt(new PixelDistance(2.0))
                .inContext(ctx());

            assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(false);
            assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(false);
            assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(2.2))).isEqualTo(true);
        }

        @Test
        void percent() {
            Predicate<Distance> constraint = gt(new PercentageDistance(50.0))
                .inContext(ctx()
                    .dimension(HORIZONTAL)
                    .bounds(new Bounds(0, 0, 10, 2)));

            assertThat(constraint.test(new PixelDistance(5))).isEqualTo(false);
            assertThat(constraint.test(new PixelDistance(6))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(7))).isEqualTo(true);
        }
    }

    @Test
    void testWithPrecision() {
        Predicate<Distance> constraint = gt(new PixelDistance(2.0)).withPrecision(new PixelPrecision(1))
            .inContext(ctx());

        assertThat(constraint.test(new PixelDistance(1.0))).isEqualTo(false);
        assertThat(constraint.test(new PixelDistance(1.1))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.2))).isEqualTo(true);
    }

    @Test
    void testDescription() {
        assertThat(gt(new PixelDistance(2.0)).describeIn(ctx())).isEqualTo("> 2px");
        assertThat(gt(new PixelDistance(2.3)).describeIn(ctx())).isEqualTo("> 2.3px");
    }

}
