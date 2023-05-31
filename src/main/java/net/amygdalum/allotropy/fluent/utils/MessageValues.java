package net.amygdalum.allotropy.fluent.utils;

import java.text.NumberFormat;
import java.util.Locale;

public final class MessageValues {
    private static final NumberFormat FORMAT = numberFormat();

    private MessageValues() {
    }

    private static NumberFormat numberFormat() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    public static String value(Object value) {
        if (value instanceof String string) {
            return '"' + escape(string) + '"';
        } else if (value instanceof Double dvalue) {
            return FORMAT.format(dvalue);
        } else if (!value.getClass().isPrimitive()) {
            return '<' + value.toString() + '>';
        } else {
            return value.toString();
        }
    }

    private static String escape(String raw) {
        return raw.replaceAll("\"", "\\\"");
    }
}
