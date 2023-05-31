package net.amygdalum.allotropy.fluent.distances;

public class AsPixels implements DistanceFactory {

    @Override
    public Distance from(double units) {
        return new PixelDistance(units);
    }

}
