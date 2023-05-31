package net.amygdalum.allotropy.fluent.elements;

import static net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.TRUE;
import static net.amygdalum.allotropy.fluent.elements.BoolWithExplanation.fail;
import static net.amygdalum.allotropy.fluent.precision.Precision.exact;
import static net.amygdalum.allotropy.fluent.utils.Exceptions.defaultInExhaustiveMatch;

import net.amygdalum.allotropy.fluent.alignment.Alignment;
import net.amygdalum.allotropy.fluent.alignment.Alignment.Boundary;
import net.amygdalum.allotropy.fluent.alignment.Alignment.Center;
import net.amygdalum.allotropy.fluent.precision.Precisable;
import net.amygdalum.allotropy.fluent.precision.Precision;

public record VisualOperand(Precision precision, Bounds bounds) implements Precisable<VisualOperand> {

    public static VisualOperand op(VisualElement element) {
        return new VisualOperand(exact(), element.bounds());
    }

    @Override
    public VisualOperand withPrecision(Precision precision) {
        return new VisualOperand(precision, bounds);
    }

    public BoolWithExplanation nextTo(VisualOperand that) {
        if (!precision.lt(this.leftDistance(that), 0) && !precision.lt(this.rightDistance(that), 0)) {
            if (this.topDistance(that) >= 0 && this.bottomDistance(that) >= 0) {
                return fail("inside");
            } else if (this.topDistanceToBottom(that) * this.bottomDistanceToTop(that) >= 0) {
                return fail("overlapping vertically");
            } else {
                return fail("stacked");
            }
        }
        if (!precision.ge(this.leftDistanceToRight(that), 0) && !precision.ge(this.rightDistanceToLeft(that), 0)) {
            return fail("displaced");
        }
        if (!precision.ge(this.topDistance(that), 0) && !precision.le(this.bottomDistance(that), 0)
            || !precision.le(this.topDistance(that), 0) && !precision.ge(this.bottomDistance(that), 0)) {
            return fail("displaced");
        }
        return TRUE;
    }

    public BoolWithExplanation stacked(VisualOperand that) {
        if (!precision.lt(this.topDistance(that), 0) && !precision.lt(this.bottomDistance(that), 0)) {
            if (this.leftDistance(that) >= 0 && this.rightDistance(that) >= 0) {
                return fail("inside");
            } else if (this.leftDistanceToRight(that) * this.rightDistanceToLeft(that) >= 0) {
                return fail("overlapping horizontally");
            } else {
                return fail("next to");
            }
        }
        if (!precision.ge(this.topDistanceToBottom(that), 0) && !precision.ge(this.bottomDistanceToTop(that), 0)) {
            return fail("displaced");
        }
        if (!precision.ge(this.leftDistance(that), 0) && !precision.le(this.rightDistance(that), 0)
            || !precision.le(this.leftDistance(that), 0) && !precision.ge(this.rightDistance(that), 0)) {
            return fail("displaced");
        }
        return TRUE;
    }

    public boolean around(VisualOperand that) {
        return this.topDistance(that) <= 0
            && this.rightDistance(that) <= 0
            && this.bottomDistance(that) <= 0
            && this.leftDistance(that) <= 0;
    }

    public boolean inside(VisualOperand that) {
        return this.topDistance(that) >= 0
            && this.rightDistance(that) >= 0
            && this.bottomDistance(that) >= 0
            && this.leftDistance(that) >= 0;
    }

    public boolean outside(VisualOperand that) {
        return this.topDistanceToBottom(that) > 0
            || this.bottomDistanceToTop(that) > 0
            || this.leftDistanceToRight(that) > 0
            || this.rightDistanceToLeft(that) > 0;
    }

    public boolean overlapping(VisualOperand that) {
        return this.topDistanceToBottom(that) <= 0
            && this.bottomDistanceToTop(that) <= 0
            && this.leftDistanceToRight(that) <= 0
            && this.rightDistanceToLeft(that) <= 0
            && (this.topDistance(that) < 0 || this.rightDistance(that) < 0 || this.bottomDistance(that) < 0 || this.leftDistance(that) < 0)
            && (this.topDistance(that) > 0 || this.rightDistance(that) > 0 || this.bottomDistance(that) > 0 || this.leftDistance(that) > 0);
    }

    public double topDistance(VisualOperand that) {
        return this.bounds.top() - that.bounds.top();
    }

    public double rightDistance(VisualOperand that) {
        return that.bounds().right() - this.bounds().right();
    }

    public double bottomDistance(VisualOperand that) {
        return that.bounds().bottom() - this.bounds().bottom();
    }

    public double leftDistance(VisualOperand that) {
        return this.bounds().left() - that.bounds().left();
    }

    public double topDistanceToBottom(VisualOperand that) {
        return this.bounds.top() - that.bounds.bottom() - 1;
    }

    public double rightDistanceToLeft(VisualOperand that) {
        return that.bounds().left() - this.bounds().right() - 1;
    }

    public double bottomDistanceToTop(VisualOperand that) {
        return that.bounds().top() - this.bounds().bottom() - 1;
    }

    public double leftDistanceToRight(VisualOperand that) {
        return this.bounds().left() - that.bounds().right() - 1;
    }

    public double at(Alignment alignment) {
        if (alignment instanceof Boundary boundary) {
            return this.bounds.bound(boundary.direction());
        } else if (alignment instanceof Center center) {
            return this.bounds.center(center.dimension());
        } else {
            throw defaultInExhaustiveMatch();
        }
    }

}
