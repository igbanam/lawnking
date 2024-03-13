package lawnking.domain;

import java.util.LinkedList;

import lawnking.domain.actors.Mower;
import lawnking.domain.landscape.Square;
import lawnking.domain.landscape.Surrounding;
import lawnking.domain.messages.SurroundingMessage;

public final class LocalWorldView extends WorldView<LinkedList<LinkedList<Square>>> {

    // This is the default constructor because for the local world view, the
    // agent is only knowledgeable of the areas as discovered
    public LocalWorldView() {
        super(0, 0);
    }

    private LocalWorldView(Integer width, Integer length) {
        super(width, length);
    }

    @Override
    public LinkedList<LinkedList<Square>> createGrid() {
        LinkedList<Square> inner = new LinkedList<>();
        return new LinkedList<>() {{
            add(inner);
        }};
    }

    @Override
    public void register(Square s, Point c) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public void register(Mower m, Point c) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public Surrounding getSurrounding(Point location) {
        throw new UnsupportedOperationException("Unimplemented method 'getSurrounding'");
    }

    public void update(SurroundingMessage message) {
        // need to expand the world around location with the current surrounding
        Surrounding surrounding = message.getSurrounding();
        expand(surrounding);
    }

    private void expand(Surrounding surrounding) {
        var virtualGrid = this.getGrid();
        // the assumption here — which is weak at best — is that the agent is at
        // 0,0 always. If the agent moves, the new position of the agent remains
        // 0,0 and the world shifts to accommodate this.
        this.setGrid(virtualGrid);
        throw new UnsupportedOperationException("Unimplemented method 'expand'");
    }
}
