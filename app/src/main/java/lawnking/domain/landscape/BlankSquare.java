package lawnking.domain.landscape;

import lawnking.domain.Visitor;

public class BlankSquare extends Square {

    @Override
    public void accept(Visitor v) {
        System.out.println("On a blank square");
    }

    @Override
    public String serialize() {
        return "b";
    }
}
