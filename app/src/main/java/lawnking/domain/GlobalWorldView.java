package lawnking.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lawnking.domain.actors.Mower;
import lawnking.domain.landscape.BlankSquare;
import lawnking.domain.landscape.Square;
import lawnking.domain.landscape.Surrounding;

public final class GlobalWorldView extends WorldView<Square[][]> {

    public GlobalWorldView(Integer width, Integer length) {
        super(width, length);
        this.setGrid(createGrid());
    }

    @Override
    public Square[][] createGrid() {
        Square[][] result = new Square[getWidth()][getLength()];
        Arrays
            .stream(result)
            .forEach(row -> Arrays.fill(row, new BlankSquare()));
        return result;
    }

    public void populateGrid() {}

    @Override
    public void register(Square square, Point location) {
        this.getGrid()[location.getX()][location.getY()] = square;
    }

    @Override
    public void register(Mower mower, Point location) {
        // this.getGrid()[location.getX()][location.getY()] = mower;
    }

    @Override
    public Surrounding getSurrounding(Point location) {
        Point north = Point.northOf(location);
        Point northEast = Point.eastOf(north);
        Point northWest = Point.westOf(north);
        Point south = Point.southOf(location);
        Point southEast = Point.eastOf(south);
        Point southWest = Point.westOf(south);
        Point east = Point.eastOf(location);
        Point west = Point.westOf(location);


        List<Square> surrounding;

        surrounding = Arrays.asList(
                north,
                northEast,
                east,
                southEast,
                south,
                southWest,
                west,
                northWest)
            .stream()
            .map(p -> withinBounds(p) ? at(p) : null)
            .collect(Collectors.toList());

        return new Surrounding(surrounding);
    }

    /**
     * Get the {@link Square} at the particular {@link Point} p
     * @param p
     * @return {@link Square}
     */
    private Square at(Point p) {
        return getGrid()[p.getX()][p.getY()];
    }

    private boolean withinBounds(Point p) {
        return p.getX() >= 0
            && p.getX() < getWidth()
            && p.getY() >= 0
            && p.getY() < getLength();
    }
}
