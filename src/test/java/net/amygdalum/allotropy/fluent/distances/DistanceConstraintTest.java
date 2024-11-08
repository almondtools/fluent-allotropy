package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.distances.AssertionContext.ctx;
import static net.amygdalum.allotropy.fluent.distances.DistanceConstraint.NONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.precision.PixelPrecision;

class DistanceConstraintTest {

    @Test
    void testTest() {
        Predicate<Distance> none = NONE.inContext(ctx());
        assertThat(none.test(new PixelDistance(10000))).isEqualTo(true);
        assertThat(none.test(new PixelDistance(0))).isEqualTo(true);
        assertThat(none.test(new PixelDistance(-10000))).isEqualTo(true);
    }

    @Test
    void testWithPrecision() {
        Predicate<Distance> alsoNone = NONE.withPrecision(new PixelPrecision(0))
            .inContext(ctx());
        assertThat(alsoNone.test(new PixelDistance(10000))).isEqualTo(true);
        assertThat(alsoNone.test(new PixelDistance(0))).isEqualTo(true);
        assertThat(alsoNone.test(new PixelDistance(-10000))).isEqualTo(true);
    }

    @Test
    void testDescription() {
        assertThat(NONE.describeIn(ctx())).isEqualTo("any");
    }

}
