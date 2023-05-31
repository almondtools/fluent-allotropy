package net.amygdalum.allotropy.fluent.canvas;

public record EdgeCharacteristics(int bits) {

    private static int ZERO = 0b0000;
    private static int N = 0b1000;
    private static int E = 0b0100;
    private static int S = 0b0010;
    private static int W = 0b0001;

    public static EdgeCharacteristics characteristics(char c) {
        return switch (c) {
        case '═' -> new EdgeCharacteristics(E | W);
        case '║' -> new EdgeCharacteristics(N | S);
        case '╔' -> new EdgeCharacteristics(E | S);
        case '╗' -> new EdgeCharacteristics(S | W);
        case '╚' -> new EdgeCharacteristics(N | E);
        case '╝' -> new EdgeCharacteristics(N | W);
        case '╦' -> new EdgeCharacteristics(E | S | W);
        case '╩' -> new EdgeCharacteristics(N | E | W);
        case '╠' -> new EdgeCharacteristics(N | E | S);
        case '╣' -> new EdgeCharacteristics(N | S | W);
        case '╬' -> new EdgeCharacteristics(N | E | S | W);
        default -> new EdgeCharacteristics(ZERO);
        };
    }

    public EdgeCharacteristics merge(EdgeCharacteristics that) {
        return new EdgeCharacteristics(this.bits | that.bits);
    }

    public char toChar() {
        if (bits == (E | W)) {
            return '═';
        } else if (bits == (N | S)) {
            return '║';
        } else if (bits == (E | S)) {
            return '╔';
        } else if (bits == (S | W)) {
            return '╗';
        } else if (bits == (N | E)) {
            return '╚';
        } else if (bits == (N | W)) {
            return '╝';
        } else if (bits == (E | S | W)) {
            return '╦';
        } else if (bits == (N | E | W)) {
            return '╩';
        } else if (bits == (N | E | S)) {
            return '╠';
        } else if (bits == (N | S | W)) {
            return '╣';
        } else if (bits == (N | E | S | W)) {
            return '╬';
        } else {
            return ' ';
        }
    }

}
