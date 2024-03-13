package lawnking.domain.landscape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Surrounding {
    private static final Map<String, Integer> CLOCKWISE_INDEXING =
        new HashMap<String, Integer>() {{
            put("north", 0);
            put("north-east", 1);
            put("east", 2);
            put("south-east", 3);
            put("south", 4);
            put("south-west", 5);
            put("west", 6);
            put("north-west", 7);
        }};
    public Surrounding(List<Square> area) {
        this.area = area;
    }

    private List<Square> area;

    public List<Square> getArea() {
        return area;
    }

    public Square getNorth() {
        return area.get(CLOCKWISE_INDEXING.get("north"));
    }

    public Square getSouth() {
        return area.get(CLOCKWISE_INDEXING.get("south"));
    }

    public Square getEast() {
        return area.get(CLOCKWISE_INDEXING.get("east"));
    }

    public Square getWest() {
        return area.get(CLOCKWISE_INDEXING.get("west"));
    }

    public Square getNorthWest() {
        return area.get(CLOCKWISE_INDEXING.get("north-west"));
    }

    public Square getNorthEast() {
        return area.get(CLOCKWISE_INDEXING.get("north-east"));
    }

    public Square getSouthEast() {
        return area.get(CLOCKWISE_INDEXING.get("south-east"));
    }

    public Square getSouthWest() {
        return area.get(CLOCKWISE_INDEXING.get("south-west"));
    }

    public String serialize() {
        return area
            .stream()
            .map(s -> s.serialize())
            .collect(Collectors.joining());
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static Surrounding from(String serializedSurrounding) {
        List<Square> result = null;
        try {
            result = serializedSurrounding
                .chars()
                .mapToObj(c -> SquareFactory.createFrom((char) c))
                .collect(Collectors.toList());
        } catch (IllegalStateException e) {
            Logger.getGlobal().severe(String.format("Current surrounding is -- %s", serializedSurrounding));
            throw e;
        }
        return new Surrounding(result);
    }
}

