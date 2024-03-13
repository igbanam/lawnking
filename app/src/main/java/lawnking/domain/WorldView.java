package lawnking.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lawnking.domain.actors.Mower;
import lawnking.domain.landscape.Square;
import lawnking.domain.landscape.Surrounding;

public abstract sealed class WorldView<T> permits GlobalWorldView, LocalWorldView {
    private Integer width;
    private Integer length;

    private T grid;
    protected void setGrid(T grid) {
        this.grid = grid;
    }

    public WorldView(Integer width, Integer length) {
        this.width = width;
        this.length = length;

        this.createGrid();
    }

    public abstract T createGrid();

    public Integer getWidth() {
        return width;
    }

    public Integer getLength() {
        return length;
    }

    public T getGrid() {
        return grid;
    }

    public abstract void register(Square s, Point c);
    public abstract void register(Mower m, Point c);

    public abstract Surrounding getSurrounding(Point location);

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
