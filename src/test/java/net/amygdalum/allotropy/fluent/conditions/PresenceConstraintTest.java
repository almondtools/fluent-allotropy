package net.amygdalum.allotropy.fluent.conditions;

import static net.amygdalum.allotropy.fluent.conditions.PresenceConstraint.isAbsent;
import static net.amygdalum.allotropy.fluent.conditions.PresenceConstraint.isPresent;
import static net.amygdalum.allotropy.fluent.elements.ElementProperty.DISPLAYED;
import static net.amygdalum.allotropy.fluent.elements.VisualElements.visualElement;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.allotropy.fluent.elements.VisualElement;

class PresenceConstraintTest {

    @Test
    void testTest() {
        VisualElement displayed = visualElement().withProperties(DISPLAYED);
        VisualElement notdisplayed = visualElement().withoutProperties(DISPLAYED);
        VisualElement nondisplayable = new NonDisplayable();

        assertThat(isPresent().test(displayed)).isEqualTo(true);
        assertThat(isPresent().test(notdisplayed)).isEqualTo(false);
        assertThat(isPresent().test(nondisplayable)).isEqualTo(false);
        assertThat(isAbsent().test(displayed)).isEqualTo(false);
        assertThat(isAbsent().test(notdisplayed)).isEqualTo(true);
        assertThat(isAbsent().test(nondisplayable)).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(isPresent().description()).isEqualTo("present");
        assertThat(isAbsent().description()).isEqualTo("absent");
    }

}
