package net.amygdalum.allotropy.fluent.canvas;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;
import org.opentest4j.AssertionFailedError;

public class CanvasExtension implements TestInstancePostProcessor, AfterEachCallback {

    private static final Namespace NS = Namespace.create(CanvasExtension.class);

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        List<Field> fields = AnnotationSupport.findAnnotatedFields(context.getRequiredTestClass(), Canvas.class);
        Object instance = testInstance;
        for (var field : fields) {
            Canvas canvas = AnnotationSupport.findAnnotation(field, Canvas.class).orElseThrow();
            AsciiCanvas asciiCanvas = new AsciiCanvas(canvas.width(), canvas.height());
            AsciiCanvasList printed = context.getStore(NS).getOrComputeIfAbsent(AsciiCanvasList.class, k -> new AsciiCanvasList(), AsciiCanvasList.class);
            if (canvas.print()) {
                printed.add(asciiCanvas);
            }
            field.trySetAccessible();
            field.set(instance, asciiCanvas);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        AsciiCanvasList printed = context.getStore(NS).getOrComputeIfAbsent(AsciiCanvasList.class, k -> new AsciiCanvasList(), AsciiCanvasList.class);
        Method method = context.getRequiredTestMethod();
        List<Print> prints = AnnotationSupport.findRepeatableAnnotations(method, Print.class);

        Iterator<String> expected = prints.stream().map(p -> p.value()).iterator();

        Iterator<String> found = printed.stream().map(p -> p.toString()).iterator();
        while (expected.hasNext() && found.hasNext()) {
            String e = prune(expected.next());
            String f = prune(found.next());
            if (!e.equals(f)) {
                printed.clear();
                throw new AssertionFailedError("expected print differed from found", e, f);
            }
        }
        printed.clear();
    }

    private String prune(String s) {
        if (s.isEmpty()) {
            return s;
        }

        char[][] cells = s.lines()
            .map(l -> l.toCharArray())
            .map(l -> {
                for (int i = 0; i < l.length; i++) {
                    if (l[i] == 0 || Character.isWhitespace(l[i])) {
                        l[i] = ' ';
                    }
                }
                return l;
            })
            .map(c -> {
                while (c.length > 0 && c[c.length - 1] == ' ') {
                    c = Arrays.copyOf(c, c.length - 1);
                }
                return c;
            })
            .toArray(char[][]::new);
        while (cells.length > 0 && String.valueOf(cells[0]).chars().allMatch(i -> i == ' ')) {
            char[][] newCells = new char[cells.length - 1][];
            System.arraycopy(cells, 1, newCells, 0, cells.length - 1);
            cells = newCells;
        }
        while (cells.length > 0 && String.valueOf(cells[cells.length - 1]).chars().allMatch(i -> i == ' ')) {
            char[][] newCells = new char[cells.length - 1][];
            System.arraycopy(cells, 0, newCells, 0, cells.length - 1);
            cells = newCells;
        }
        while (cells.length != 0 && Stream.of(cells).allMatch(line -> line.length == 0 || line[0] == ' ')) {
            char[][] newCells = new char[cells.length][];
            for (int i = 0; i < cells.length; i++) {
                if (cells[i].length > 0) {
                    newCells[i] = Arrays.copyOfRange(cells[i], 1, cells[i].length);
                } else {
                    newCells[i] = cells[i];
                }
            }
            cells = newCells;
        }

        return Stream.of(cells).map(l -> String.valueOf(l)).collect(joining("\n"));
    }

    private static class AsciiCanvasList extends ArrayList<AsciiCanvas> {

    }
}
