package lawnking.domain.landscape;

public class SquareFactory {
    public static Square createFrom(Character c) {
        switch (c) {
            case 'b':
                return new BlankSquare();
            case 'g':
                return new Grass();
            case 'c':
                return new Crater();
            default:
                throw new IllegalStateException("Unknown Square type in Surrounding");
        }
    }
}
