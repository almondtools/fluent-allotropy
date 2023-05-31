package net.amygdalum.allotropy.fluent.multiple;

import static net.amygdalum.allotropy.fluent.Expectations.expect;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.amygdalum.allotropy.fluent.canvas.AsciiCanvas;
import net.amygdalum.allotropy.fluent.canvas.Canvas;
import net.amygdalum.allotropy.fluent.canvas.CanvasExtension;
import net.amygdalum.allotropy.fluent.canvas.Print;

@ExtendWith(CanvasExtension.class)
class AlignedAssertTest {

    @Canvas(print = true)
    private AsciiCanvas canvas;

    @Nested
    class alignedHorizontally {
        @Test
        @Print("""
            ╔╗ ╔╗ ╔╗
            ╚╝ ╚╝ ╚╝
            """)
        void success() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .all()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .top()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .bottom()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .centered()
                .withEachOther();
        }

        @Test
        @Print("""
                  ╔╗
            ╔╗    ║║
            ║║ ╔╗ ║║
            ║║ ╚╝ ║║
            ╚╝    ║║
                  ╚╝
            """)
        void centered() {
            expect(canvas.rect(2, 3, 3, 6), canvas.rect(5, 4, 6, 5), canvas.rect(8, 2, 9, 7))
                .alignedHorizontally()
                .centered()
                .withEachOther();
        }

        @Test
        @Print("""
            ╔╗ ╔╗ ╔╗
            ╚╝ ║║ ╚╝
               ╚╝
            """)
        void withRelaxedPrecision() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 4), canvas.rect(8, 2, 9, 3))
                .alignedHorizontally()
                .all()
                .withPrecision(1).pixels()
                .withEachOther();
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ╚══╝
             """)
        void butInside() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(1, 1, 4, 4))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [1, 1] => [4, 4] but was inside.");
        }

        @Test
        @Print("""
             ╔╗
            ╔╬╬╗
            ║╚╝║
            ║  ║
            ║  ║
            ╚══╝
             """)
        void butOverlappingVertically() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4), canvas.rect(1, 3, 4, 7))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 4] aligned horizontally with [1, 3] => [4, 7] but was overlapping vertically.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔═╗
            ║ ║
            ╚═╝
             """)
        void butStacked() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 4, 7))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [2, 5] => [4, 7] but was stacked.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝ ╔╗
               ╚╝
             """)
        void butDisplacedWithVerticalOverlap() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 3, 6, 4))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [5, 3] => [6, 4] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝ ╔╗
               ╚╝ ╔╗
                  ╚╝
            """)
        void butIndirectlyDisplaced() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 3, 6, 4), canvas.rect(8, 4, 9, 5))
                .alignedHorizontally()
                .all()
                .withPrecision(1).pixels()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [8, 4] => [9, 5] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

             ╔╗
             ╚╝
             """)
        void butDisplacedWithHorizontalOverlap() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(3, 5, 4, 6))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [3, 5] => [4, 6] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

               ╔╗
               ╚╝
             """)
        void butDisplacedCompletely() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 5, 6, 6))
                .alignedHorizontally()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned horizontally with [5, 5] => [6, 6] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ║║ ╔╗
            ╚╝ ╚╝
             """)
        void butMissedTopConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4), canvas.rect(5, 3, 6, 4))
                .alignedHorizontally()
                .top()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 4] aligned horizontally at top with [5, 3] => [6, 4] but not at top.");

            error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4), canvas.rect(5, 3, 6, 4))
                .alignedHorizontally()
                .all()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 4] aligned horizontally at top with [5, 3] => [6, 4] but not at top.");
        }

        @Test
        @Print("""
            ╔╗ ╔╗
            ║║ ╚╝
            ╚╝
             """)
        void butMissedBottomConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4), canvas.rect(5, 2, 6, 3))
                .alignedHorizontally()
                .bottom()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 4] aligned horizontally at bottom with [5, 2] => [6, 3] but not at bottom.");

            error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 4), canvas.rect(5, 2, 6, 3))
                .alignedHorizontally()
                .all()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 4] aligned horizontally at bottom with [5, 2] => [6, 3] but not at bottom.");
        }

        @Test
        @Print("""
            ╔╗
            ║║ ╔╗ ╔╗
            ║║ ║║ ╚╝
            ║║ ╚╝
            ╚╝
             """)
        void butMissedCenteredConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 6), canvas.rect(5, 3, 6, 5), canvas.rect(8, 3, 9, 4))
                .alignedHorizontally()
                .centered()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 6] aligned horizontally centered with [8, 3] => [9, 4] but not centered.");
        }

        @Test
        @Print("""
            ╔╗ ╔╗
            ╚╝ ╚╝
            """)
        void invalidConstraint() {
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3))
                .alignedHorizontally()
                .right()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("cannot align horizontally at right");

            error = assertThrows(IllegalArgumentException.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 6, 3))
                .alignedHorizontally()
                .left()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("cannot align horizontally at left");

        }

    }

    @Nested
    class alignedVertically {
        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔╗
            ╚╝

            ╔╗
            ╚╝
            """)
        void success() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .all()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .right()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .left()
                .withEachOther();
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .centered()
                .withEachOther();
        }

        @Test
        @Print("""
            ╔════╗
            ╚════╝

              ╔╗
              ╚╝

             ╔══╗
             ╚══╝
            """)
        void centered() {
            expect(canvas.rect(2, 2, 7, 3), canvas.rect(4, 5, 5, 6), canvas.rect(3, 8, 6, 9))
                .alignedVertically()
                .centered()
                .withEachOther();
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔═╗
            ╚═╝

             ╔╗
             ╚╝
            """)
        void withRelaxedPrecision() {
            expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 4, 6), canvas.rect(3, 8, 4, 9))
                .alignedVertically()
                .all()
                .withPrecision(1).pixels()
                .withEachOther();
        }

        @Test
        @Print("""
            ╔══╗
            ║╔╗║
            ║╚╝║
            ╚══╝
             """)
        void butInside() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(1, 1, 4, 4))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned vertically with [1, 1] => [4, 4] but was inside.");
        }

        @Test
        @Print("""
             ╔═══╗
            ╔╬╗  ║
            ╚╬╝  ║
             ╚═══╝
             """)
        void butOverlappingHorizontally() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 4, 3), canvas.rect(3, 1, 7, 4))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [4, 3] aligned vertically with [3, 1] => [7, 4] but was overlapping horizontally.");
        }

        @Test
        @Print("""
            ╔╗ ╔═╗
            ╚╝ ║ ║
               ╚═╝
             """)
        void butNextTo() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 2, 7, 4))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned vertically with [5, 2] => [7, 4] but was next to.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

             ╔╗
             ╚╝
             """)
        void butDisplacedWithHorizontalOverlap() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(3, 5, 4, 6))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned vertically with [3, 5] => [4, 6] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

             ╔╗
             ╚╝

              ╔╗
              ╚╝
            """)
        void butIndirectlyDisplaced() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 1, 3, 2), canvas.rect(3, 4, 4, 5), canvas.rect(4, 7, 5, 8))
                .alignedHorizontally()
                .all()
                .withPrecision(1).pixels()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 1] => [3, 2] aligned horizontally with [3, 4] => [4, 5] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝ ╔╗
               ╚╝
             """)
        void butDisplacedWithVerticalOverlap() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 3, 6, 4))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned vertically with [5, 3] => [6, 4] but was displaced.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

               ╔╗
               ╚╝
             """)
        void butDisplacedCompletely() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(5, 5, 6, 6))
                .alignedVertically()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [3, 3] aligned vertically with [5, 5] => [6, 6] but was displaced.");
        }

        @Test
        @Print("""
            ╔═╗
            ╚═╝

             ╔╗
             ╚╝
             """)
        void butMissedLeftConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 4, 3), canvas.rect(3, 5, 4, 6))
                .alignedVertically()
                .left()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [4, 3] aligned vertically at left with [3, 5] => [4, 6] but not at left.");

            error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 4, 3), canvas.rect(3, 5, 4, 6))
                .alignedVertically()
                .all()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [4, 3] aligned vertically at left with [3, 5] => [4, 6] but not at left.");
        }

        @Test
        @Print("""
            ╔═╗
            ╚═╝

            ╔╗
            ╚╝
             """)
        void butMissedRightConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 4, 3), canvas.rect(2, 5, 3, 6))
                .alignedVertically()
                .right()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [4, 3] aligned vertically at right with [2, 5] => [3, 6] but not at right.");

            error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 4, 3), canvas.rect(2, 5, 3, 6))
                .alignedVertically()
                .all()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [4, 3] aligned vertically at right with [2, 5] => [3, 6] but not at right.");
        }

        @Test
        @Print("""
            ╔══╗
            ╚══╝

             ╔╗
             ╚╝

            ╔╗
            ╚╝
             """)
        void butMissedCenteredConstraint() {
            AssertionError error = assertThrows(AssertionError.class, () -> expect(canvas.rect(2, 2, 5, 3), canvas.rect(3, 5, 4, 6), canvas.rect(2, 8, 3, 9))
                .alignedVertically()
                .centered()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("expected [2, 2] => [5, 3] aligned vertically centered with [2, 8] => [3, 9] but not centered.");
        }

        @Test
        @Print("""
            ╔╗
            ╚╝

            ╔╗
            ╚╝
            """)
        void invalidConstraint() {
            IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6))
                .alignedVertically()
                .top()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("cannot align vertically at top");

            error = assertThrows(IllegalArgumentException.class, () -> expect(canvas.rect(2, 2, 3, 3), canvas.rect(2, 5, 3, 6))
                .alignedVertically()
                .bottom()
                .withEachOther());
            assertThat(error.getMessage()).isEqualTo("cannot align vertically at bottom");

        }
    }
}
