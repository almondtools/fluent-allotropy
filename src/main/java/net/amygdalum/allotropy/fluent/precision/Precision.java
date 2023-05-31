package net.amygdalum.allotropy.fluent.precision;

public interface Precision {

    public static final Precision EXACT = new Precision() {

        @Override
        public boolean lt(double s, double o) {
            return s < o;
        }

        @Override
        public boolean le(double s, double o) {
            return s <= o;
        }

        @Override
        public boolean eq(double s, double o) {
            return s == o;
        }

        @Override
        public boolean ge(double s, double o) {
            return s >= o;
        }

        @Override
        public boolean gt(double s, double o) {
            return s > o;
        }

        @Override
        public String description() {
            return "exact";
        }

    };

    public static Precision exact() {
        return EXACT;
    }

    boolean lt(double s, double o);

    boolean le(double s, double o);

    boolean eq(double s, double o);

    boolean ge(double s, double o);

    boolean gt(double s, double o);

    String description();
}
