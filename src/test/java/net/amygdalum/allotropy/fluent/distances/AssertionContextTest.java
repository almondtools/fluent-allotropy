package net.amygdalum.allotropy.fluent.distances;

import static net.amygdalum.allotropy.fluent.dimensions.Dimension.HORIZONTAL;
import static net.amygdalum.allotropy.fluent.dimensions.Dimension.VERTICAL;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.Bounds;

class AssertionContextTest {

    @Nested
    class testBounds {
        @Test
        void present() {
            var bounds = new Bounds(1, 1, 4, 4);
            var ctx = AssertionContext.ctx().bounds(bounds);

            assertThat(ctx.bounds()).isEqualTo(bounds);
        }

        @Test
        void optional() {
            var bounds = new Bounds(1, 1, 4, 4);
            var ctx = AssertionContext.ctx().bounds(Optional.of(bounds));

            assertThat(ctx.bounds()).isEqualTo(bounds);
        }

        @Test
        void none() {
            var ctx = AssertionContext.ctx().bounds(Optional.empty());

            MissingContextException e = assertThrows(MissingContextException.class, () -> ctx.bounds());

            assertThat(e.getMessage()).isEqualTo("no context information on class Bounds");
        }
    }

    @Nested
    class testDirection {
    @Test
    void present() {
        var ctx = AssertionContext.ctx().direction(N);

        assertThat(ctx.direction()).isEqualTo(N);
    }
    @Test
    void optional() {
        var ctx = AssertionContext.ctx().direction(Optional.of(N));
        
        assertThat(ctx.direction()).isEqualTo(N);
    }
    @Test
    void none() {
        var ctx = AssertionContext.ctx().direction(Optional.empty());
        
        MissingContextException e = assertThrows(MissingContextException.class, () -> ctx.direction());

        assertThat(e.getMessage()).isEqualTo("no context information on class CardinalDirection");
    }
    }

    @Nested
    class testDimension {
        @Test
        void immediate() {
            var ctx = AssertionContext.ctx().dimension(HORIZONTAL);

            assertThat(ctx.dimension()).isEqualTo(HORIZONTAL);
        }

        @Test
        void derived() {
            var ctx = AssertionContext.ctx().direction(N);

            assertThat(ctx.dimension()).isEqualTo(VERTICAL);
        }

        @Test
        void none() {
            var ctx = AssertionContext.ctx()
                .direction(Optional.empty())
                .dimension(Optional.empty());

            MissingContextException e = assertThrows(MissingContextException.class, () -> ctx.dimension());

            assertThat(e.getMessage()).isEqualTo("no context information on class Dimension");
        }
}

    @Test
    void testAdd() {
        var bounds1 = new Bounds(1, 1, 4, 4);
        var bounds2 = new Bounds(2, 2, 5, 5);
        AmbiguousContextException e = assertThrows(AmbiguousContextException.class, () -> AssertionContext.ctx().bounds(bounds1).bounds(bounds2));

        assertThat(e.getMessage()).isEqualTo("more than one information on class Bounds");
    }

}
