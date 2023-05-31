package net.amygdalum.allotropy.fluent.directions;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.E;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.N;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.S;
import static net.amygdalum.allotropy.fluent.directions.CardinalDirection.W;

public enum IntercardinalDirection implements Direction {
    NE(N,E), SE(S,E), SW(S,W), NW(N,W);

    private CardinalDirection primary;
    private CardinalDirection secondary;

    private IntercardinalDirection(CardinalDirection primary,CardinalDirection secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public String label() {
        return primary.label() + " " + secondary.label();
    }

}
