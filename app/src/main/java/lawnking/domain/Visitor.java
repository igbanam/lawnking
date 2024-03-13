package lawnking.domain;

import lawnking.domain.landscape.Square;

public interface Visitor {
    void visit(Square s);
}

