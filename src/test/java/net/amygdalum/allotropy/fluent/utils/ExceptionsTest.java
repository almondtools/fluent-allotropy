package net.amygdalum.allotropy.fluent.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static net.amygdalum.allotropy.fluent.utils.Exceptions.defaultInExhaustiveMatch;

import org.junit.jupiter.api.Test;

class ExceptionsTest {

    @Test
    void testDefaultInExhaustiveMatch() {
        assertThat(defaultInExhaustiveMatch()).isInstanceOf(IllegalStateException.class);
    }

}
