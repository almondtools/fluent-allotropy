package net.amygdalum.allotropy.fluent.elements;

import java.util.Map;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.directions.CardinalDirection;

public record Bounds(int left, int top, int right, int bottom) {

    public static Bounds from(Object object) {
        if (object instanceof Bounds bounds) {
            return bounds;
        } else if (object instanceof Map<?, ?> map) {
            try {
                int x = Float.valueOf(map.get("x").toString()).intValue();
                int y = Float.valueOf(map.get("y").toString()).intValue();
                int width = Float.valueOf(map.get("width").toString()).intValue();
                int height = Float.valueOf(map.get("height").toString()).intValue();
                return new Bounds(x, y, x + width - 1, y + height - 1);
            } catch (NullPointerException | IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double bound(CardinalDirection direction) {
        return switch (direction) {
        case N -> top();
        case E -> right();
        case S -> bottom();
        case W -> left();
        };
    }

    public double center(Dimension dimension) {
        return switch (dimension) {
        case HORIZONTAL -> (left() + right()) / 2.0;
        case VERTICAL -> (top() + bottom()) / 2.0;
        };
    }

    public Point bottomLeft() {
        return new Point(left(), bottom());
    }

    public Point bottomRight() {
        return new Point(right(), bottom());
    }

    public Point topLeft() {
        return new Point(left(), top());
    }

    public Point topRight() {
        return new Point(right(), top());
    }

    public int width() {
        return right - left + 1;
    }

    public int height() {
        return bottom - top + 1;
    }

    public int size(Dimension dimension) {
        return switch (dimension) {
        case HORIZONTAL -> width();
        case VERTICAL -> height();
        };
    }

    public boolean contains(Point p) {
        return top <= p.y()
            && bottom >= p.y()
            && left <= p.x()
            && right >= p.x();
    }

    public String toString() {
        return "[" + left + ", " + top + "] => [" + right + ", " + bottom + "]";
    }

}
