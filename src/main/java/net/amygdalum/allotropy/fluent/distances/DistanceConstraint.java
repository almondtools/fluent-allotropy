package net.amygdalum.allotropy.fluent.distances;

import java.util.function.Predicate;

import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.Precision;

public interface DistanceConstraint extends Precisable<DistanceConstraint> {

    Predicate<Distance> inContext(AssertionContext context);

    String describeIn(AssertionContext context);

    DistanceConstraint NONE = new DistanceConstraint() {

        @Override
        public Predicate<Distance> inContext(AssertionContext context) {
            return c -> true;
        }

        @Override
        public DistanceConstraint withPrecision(Precision precision) {
            return this;
        }

        @Override
        public String describeIn(AssertionContext context) {
            return "any";
        }
    };

}
