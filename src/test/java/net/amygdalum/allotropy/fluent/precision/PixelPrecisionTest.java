package net.amygdalum.allotropy.fluent.precision;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PixelPrecisionTest {

    @Test
    void testLt() {
        PixelPrecision prec = new PixelPrecision(2);
        assertThat(prec.lt(1, 1.1)).isEqualTo(true);

        assertThat(prec.lt(1, -0.9)).isEqualTo(true);
        assertThat(prec.lt(1, -1)).isEqualTo(false);
    }

    @Test
    void testLe() {
        PixelPrecision prec = new PixelPrecision(2);
        assertThat(prec.le(1, 1.1)).isEqualTo(true);

        assertThat(prec.le(1, -1)).isEqualTo(true);
        assertThat(prec.le(1, -1.1)).isEqualTo(false);
    }

    @Test
    void testGt() {
        PixelPrecision prec = new PixelPrecision(2);
        assertThat(prec.gt(1, 0.9)).isEqualTo(true);

        assertThat(prec.gt(1, 2.9)).isEqualTo(true);
        assertThat(prec.gt(1, 3)).isEqualTo(false);
    }

    @Test
    void testGe() {
        PixelPrecision prec = new PixelPrecision(2);
        assertThat(prec.ge(1, 0.9)).isEqualTo(true);

        assertThat(prec.ge(1, 3)).isEqualTo(true);
        assertThat(prec.ge(1, 3.1)).isEqualTo(false);
    }

    @Test
    void testEq() {
        PixelPrecision prec = new PixelPrecision(2);
        assertThat(prec.eq(1, 1)).isEqualTo(true);

        assertThat(prec.eq(1, -1)).isEqualTo(true);
        assertThat(prec.eq(1, -1.1)).isEqualTo(false);

        assertThat(prec.eq(1, 3)).isEqualTo(true);
        assertThat(prec.eq(1, 3.1)).isEqualTo(false);
    }

    @Test
    void testDescription() {
        assertThat(new PixelPrecision(2).description()).isEqualTo("~2.0px");
    }

}
