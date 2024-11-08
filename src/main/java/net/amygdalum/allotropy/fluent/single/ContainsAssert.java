package net.amygdalum.allotropy.fluent.single;

import static net.amygdalum.allotropy.fluent.utils.Arrays.toArray;

import java.util.Arrays;

import org.openqa.selenium.WebElement;

import net.amygdalum.allotropy.fluent.common.Assert;
import net.amygdalum.allotropy.fluent.directional.BinaryProtoDirectionalDistanceConstraint;
import net.amygdalum.allotropy.fluent.directional.DirectionalDistanceConstrainable;
import net.amygdalum.allotropy.fluent.directional.DirectionalDistanceConstraintBuilder;
import net.amygdalum.allotropy.fluent.directional.UnaryProtoDirectionalDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.BetweenDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.EqDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.GtDistanceConstraint;
import net.amygdalum.allotropy.fluent.distances.LtDistanceConstraint;
import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;
import net.amygdalum.allotropy.fluent.elements.WebVisualElement;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.ProtoPrecision;

public interface ContainsAssert<T extends VisualElement> extends Precisable<ContainsAssert<T>>, DirectionalDistanceConstrainable<ContainsAssert<T>>, Assert {

    default AndAssert<T> items(WebElement... items) {
        return itemElements(Arrays.stream(items)
            .map(WebVisualElement::new)
            .collect(toArray()));
    }

    default AndAssert<T> items(AsVisualElement<?>... items) {
        return itemElements(Arrays.stream(items)
            .map(i -> i.asVisualElement())
            .collect(toArray()));

    }

    AndAssert<T> itemElements(VisualElement... items);

    default UnaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> less(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(LtDistanceConstraint::lt));
    }

    default UnaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> greater(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(GtDistanceConstraint::gt));
    }

    default UnaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> about(double units) {
        return about(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(EqDistanceConstraint::eq));
    }

    default UnaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> about(DirectionalDistanceConstraintBuilder builder) {
        return new UnaryProtoDirectionalDistanceConstraint<>(builder, this);
    }

    default BinaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> between(double units) {
        return between(new DirectionalDistanceConstraintBuilder()
            .addUnits(units)
            .addAccumulator(BetweenDistanceConstraint::between));
    }

    default BinaryProtoDirectionalDistanceConstraint<ContainsAssert<T>> between(DirectionalDistanceConstraintBuilder builder) {
        return new BinaryProtoDirectionalDistanceConstraint<>(builder, this);
    }

    default ProtoPrecision<ContainsAssert<T>> withPrecision(double units) {
        return new ProtoPrecision<>(units, this);
    }

}
