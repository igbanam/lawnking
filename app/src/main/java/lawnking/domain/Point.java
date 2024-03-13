package lawnking.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Point {
    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Point northOf(Point p) {
        return new Point(p.getX() + 1, p.getY());
    }

    public static Point southOf(Point p) {
        return new Point(p.getX() - 1, p.getY());
    }

    public static Point westOf(Point p) {
        return new Point(p.getX(), p.getY() - 1);
    }

    public static Point eastOf(Point p) {
        return new Point(p.getX(), p.getY() + 1);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
