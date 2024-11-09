package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.distances.DistanceConstraint.NONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class DistanceConstraintTest {

    @Nested
    class testTest {
        @Test
        void pixels() {
            Predicate<Distance> none = NONE.inContext(ctx());
            assertThat(none.test(new PixelDistance(10000))).isEqualTo(true);
            assertThat(none.test(new PixelDistance(0))).isEqualTo(true);
            assertThat(none.test(new PixelDistance(-10000))).isEqualTo(true);
            assertThat(none.test(new PixelDistance(Double.MAX_VALUE))).isEqualTo(true);
            assertThat(none.test(new PixelDistance(Double.MIN_VALUE))).isEqualTo(true);
        }

        @Test
        void percent() {
            Predicate<Distance> none = NONE.inContext(ctx()
                .dimension(HORIZONTAL)
                .bounds(new Bounds(0, 0, 10, 4)));
            
            assertThat(none.test(new PercentageDistance(-100))).isEqualTo(true);
            assertThat(none.test(new PercentageDistance(0))).isEqualTo(true);
            assertThat(none.test(new PercentageDistance(10))).isEqualTo(true);
            assertThat(none.test(new PercentageDistance(100))).isEqualTo(true);
        }
    }

    @Nested
    class testWithPrecision {
        @Test
        void pixels() {
            Predicate<Distance> alsoNone = NONE.withPrecision(new PixelPrecision(0))
                .inContext(ctx());
            assertThat(alsoNone.test(new PixelDistance(10000))).isEqualTo(true);
            assertThat(alsoNone.test(new PixelDistance(0))).isEqualTo(true);
            assertThat(alsoNone.test(new PixelDistance(-10000))).isEqualTo(true);
        }

        @Test
        void withDifferentPrecisions() {
            assertThat(NONE.withPrecision(new PixelPrecision(1))
                .inContext(ctx())
                .test(new PixelDistance(1.5))).isEqualTo(true);
            assertThat(NONE.withPrecision(new PixelPrecision(0.5))
                .inContext(ctx())
                .test(new PixelDistance(1.5))).isEqualTo(true);
            assertThat(NONE.withPrecision(new PixelPrecision(0.1))
                .inContext(ctx())
                .test(new PixelDistance(1.5))).isEqualTo(true);
        }
    }

    @Test
    void testDescription() {
        assertThat(NONE.describeIn(ctx())).isEqualTo("any");
        assertThat(NONE.withPrecision(new PixelPrecision(1)).describeIn(ctx())).isEqualTo("any");
    }

}
