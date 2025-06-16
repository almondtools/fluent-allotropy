package net.amygdalum.allotropy.fluent.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MessageValuesTest {

    @Nested
    class testValue {
        @Test
        void intNumber() {
            assertThat(MessageValues.value(1)).isEqualTo("1");
        }

        @Test
        void doubleNumber() {
            assertThat(MessageValues.value(3.14)).isEqualTo("3.14");
        }

        @Test
        void character() {
            assertThat(MessageValues.value('a')).isEqualTo("'a'");
        }

        @Test
        void bool() {
            assertThat(MessageValues.value(true)).isEqualTo("true");
        }

        @Test
        void string() {
            assertThat(MessageValues.value("value")).isEqualTo("\"value\"");
        }

        @Test
        void nonPrimitive() {
            assertThat(MessageValues.value(new Rec("val"))).isEqualTo("<Rec[att=val]>");
        }
    }

    record Rec(String att) {

    }

}
