package net.amygdalum.allotropy.fluent.conditions;

import net.amygdalum.allotropy.fluent.common.Constraint;
import net.amygdalum.allotropy.fluent.elements.DisplayableElement;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public record PresenceConstraint(boolean present) implements Constraint<VisualElement> {

    @Override
    public boolean test(VisualElement t) {
        if (t instanceof DisplayableElement displayable) {
            return displayable.isDisplayed() == present;
        }
        return false;
    }

    @Override
    public String description() {
        if (present) {
            return "present";
        } else {
            return "absent";
        }
    }

    public static PresenceConstraint isPresent() {
        return new PresenceConstraint(true);
    }

    public static PresenceConstraint isAbsent() {
        return new PresenceConstraint(false);
    }

}
