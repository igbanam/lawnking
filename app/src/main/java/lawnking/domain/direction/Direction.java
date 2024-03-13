package lawnking.domain.direction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lawnking.domain.Vector;

public class Direction {
    public static Direction fromIndex(Integer index) {
        String orientation = RANGE.keySet().stream().collect(Collectors.toList()).get(index);
        return new Direction(orientation);
    }

    private static final LinkedHashMap<String, Vector> RANGE = new LinkedHashMap<>() {{
        put("North", new Vector(1, 0));
        put("NorthEast", new Vector(1, 1));
        put("East", new Vector(0, 1));
        put("SouthEast", new Vector(-1, 1));
        put("South", new Vector(-1, 0));
        put("SouthWest", new Vector(-1, -1));
        put("West", new Vector(0, -1));
        put("NorthWest", new Vector(1, -1));
    }};

    private String orientation;

    public Direction(String orientation) {
        if (!RANGE.keySet().contains(orientation)) {
            throw new UnknownDirectionException();
        }
        this.orientation = orientation;
    }

    public Direction nextDirection(Direction direction) {
        List<String> directions = RANGE.keySet().stream().collect(Collectors.toList());
        int indexOfOrientation = directions.indexOf(direction.orientation);
        return new Direction(directions.get(indexOfOrientation));
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
