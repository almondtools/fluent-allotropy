package net.amygdalum.allotropy.fluent.distances;

import net.amygdalum.allotropy.fluent.dimensions.Dimension;
import net.amygdalum.allotropy.fluent.elements.Bounds;

public class AsPercentOf implements DistanceFactory {

    private Dimension dimension;
    private Bounds bounds;

    public AsPercentOf(Dimension dimension, Bounds bounds) {
        this.dimension = dimension;
        this.bounds = bounds;
    }

    @Override
    public Distance from(double units) {
        return new PercentageDistance(units, dimension, bounds);
    }

}
