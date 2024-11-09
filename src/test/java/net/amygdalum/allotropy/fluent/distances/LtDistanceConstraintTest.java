package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint.lt;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class LtDistanceConstraintTest {

    @Nested
    class testTest {
        @Test
        void pixels() {
            Predicate<Distance> constraint = lt(new PixelDistance(2.0))
                .inContext(ctx());

            assertThat(constraint.test(new PixelDistance(1.8))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(false);
            assertThat(constraint.test(new PixelDistance(2.1))).isEqualTo(false);
        }

        @Test
        void percent() {
            Predicate<Distance> constraint = lt(new PercentageDistance(20.0))
                .inContext(ctx()
                    .dimension(HORIZONTAL)
                    .bounds(new Bounds(1, 1, 10, 2)));

            assertThat(constraint.test(new PixelDistance(1.8))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(true);
            assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(false);
        }
    }

    @Test
    void testWithPrecision() {
        Predicate<Distance> constraint = lt(new PixelDistance(2.0)).withPrecision(new PixelPrecision(1))
            .inContext(ctx());

        assertThat(constraint.test(new PixelDistance(1.8))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(1.9))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.0))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(2.9))).isEqualTo(true);
        assertThat(constraint.test(new PixelDistance(3.0))).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(lt(new PixelDistance(2.0)).describeIn(ctx())).isEqualTo("< 2px");
        assertThat(lt(new PixelDistance(2.3)).describeIn(ctx())).isEqualTo("< 2.3px");
    }

}
