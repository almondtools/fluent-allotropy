package net.amygdalum.allotropy.fluent.distances;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.Precision;

public interface DistanceConstraint extends Constraint<Distance>, Precisable<DistanceConstraint> {

    DistanceConstraint NONE = new DistanceConstraint() {

        @Override
        public boolean test(Distance dist) {
            return true;
        }

        @Override
        public DistanceConstraint withPrecision(Precision precision) {
            return this;
        }

        @Override
        public String description() {
            return "any";
        }
    };

}
