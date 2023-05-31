package net.amygdalum.allotropy.fluent.canvas;

import static net.amygdalum.allotropy.fluent.canvas.EdgeCharacteristics.characteristics;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.amygdalum.allotropy.fluent.elements.AsVisualElement;
import net.amygdalum.allotropy.fluent.elements.Bounds;
import net.amygdalum.allotropy.fluent.elements.VisualElement;

public class AsciiCanvas implements VisualElement, AsVisualElement<AsciiCanvas> {

    private char[][] canvas;

    public AsciiCanvas(int width, int height) {
        this.canvas = new char[height][width];
    }

    @Override
    public AsciiCanvas asVisualElement() {
        return this;
    }

    public CanvasVisualElement rect(int left, int top, int right, int bottom) {
        CanvasVisualElement element = new CanvasVisualElement(left, top, right, bottom);
        add(element);
        return element;
    }

    public AsciiCanvas add(VisualElement element) {
        Bounds bounds = element.bounds();
        if (bounds.width() == 0 || bounds.height() == 0) {
            return this;
        }
        int x1 = bounds.left();
        int y1 = bounds.top();
        int x2 = bounds.right();
        int y2 = bounds.bottom();
        if (x1 == x2) {
            putVertical(x1, y1);
            putVertical(x1, y2);
        } else if (y1 == y2) {
            putHorizontal(x1, y1);
            putHorizontal(x2, y1);
        } else {
            putUpperLeft(x1, y1);
            putUpperRight(x2, y1);
            putLowerLeft(x1, y2);
            putLowerRight(x2, y2);
        }
        for (var x = x1 + 1; x < x2; x++) {
            putHorizontal(x, y1);
            putHorizontal(x, y2);
        }
        for (var y = y1 + 1; y < y2; y++) {
            putVertical(x1, y);
            putVertical(x2, y);
        }
        return this;
    }

    private void putHorizontal(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '═');
    }

    private void putVertical(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '║');
    }

    private void putUpperLeft(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '╔');
    }

    private void putUpperRight(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '╗');
    }

    private void putLowerLeft(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '╚');
    }

    private void putLowerRight(int x, int y) {
        canvas[y][x] = merge(canvas[y][x], '╝');
    }

    private char merge(char c1, char c2) {
        return characteristics(c1).merge(characteristics(c2)).toChar();
    }

    @Override
    public String toString() {
        return Stream.of(canvas).map(row -> String.valueOf(row)).collect(Collectors.joining("\n"));
    }

    @Override
    public Bounds bounds() {
        return new Bounds(0, 0, canvas.length-1, canvas[0].length-1);
    }

    @Override
    public Optional<VisualElement> parent() {
        return Optional.empty();
    }

}
