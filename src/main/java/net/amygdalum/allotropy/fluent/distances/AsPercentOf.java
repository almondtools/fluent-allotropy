package net.amygdalum.allotropy.fluent.distances;

public class AsPercentOf implements DistanceFactory {

    @Override
    public Distance from(double units) {
        return new PercentageDistance(units);
    }

}
