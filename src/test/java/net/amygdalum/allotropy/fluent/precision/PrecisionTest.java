package net.amygdalum.allotropy.fluent.precision;

import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PrecisionTest {

    @Test
    void testLt() {
        Precision prec = exact();
        assertThat(prec.lt(1, 1.1)).isEqualTo(true);
        assertThat(prec.lt(1, 1.2)).isEqualTo(true);

        assertThat(prec.lt(1, 1)).isEqualTo(false);
    }

    @Test
    void testLe() {
        Precision prec = exact();
        assertThat(prec.le(1, 1.1)).isEqualTo(true);
        assertThat(prec.le(1, 1.2)).isEqualTo(true);

        assertThat(prec.le(1, 1)).isEqualTo(true);
        assertThat(prec.le(1, 0.9)).isEqualTo(false);
    }

    @Test
    void testGt() {
        Precision prec = exact();
        assertThat(prec.gt(1, 0.9)).isEqualTo(true);
        assertThat(prec.gt(1, 0.8)).isEqualTo(true);

        assertThat(prec.gt(1, 1)).isEqualTo(false);
    }

    @Test
    void testGe() {
        Precision prec = exact();
        assertThat(prec.ge(1, 0.9)).isEqualTo(true);
        assertThat(prec.ge(1, 0.8)).isEqualTo(true);

        assertThat(prec.ge(1, 1)).isEqualTo(true);
        assertThat(prec.ge(1, 1.1)).isEqualTo(false);
    }

    @Test
    void testEq() {
        Precision prec = exact();
        assertThat(prec.eq(1, 1)).isEqualTo(true);

        assertThat(prec.eq(1, 0.9)).isEqualTo(false);
        assertThat(prec.eq(1, 1.1)).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(exact().description()).isEqualTo("exact");
    }

}
