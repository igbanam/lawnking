package lawnking.domain;

import lawnking.domain.actors.Mower;
import lawnking.domain.direction.Direction;

public record MowerRegistry(Mower mower, Point location, Direction direction) {

}

