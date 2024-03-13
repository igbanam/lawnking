package lawnking.domain.landscape;

import lawnking.domain.Visitor;

public class Grass extends Square {

    @Override
    public void accept(Visitor v) {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String serialize() {
        return "g";
    }
}
