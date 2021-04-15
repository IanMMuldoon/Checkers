public class Position {
    int _x;
    int _y;

    public Position(int x, int y){
        _x = x;
        _y = y;
    }

    public static Position getPiecePosition(Piece piece) {
        int _x = 0;
        int _y = Math.abs((piece._row) - 8);
        if (piece._row % 2 == 1) {
            switch (piece._position) {
                case 1:
                    _x = 0;
                    break;
                case 2:
                    _x = 2;
                    break;
                case 3:
                    _x = 4;
                    break;
                case 4:
                    _x = 6;
                    break;
            }
        } else {
            switch (piece._position) {
                case 1:
                    _x = 1;
                    break;
                case 2:
                    _x = 3;
                    break;
                case 3:
                    _x = 5;
                    break;
                case 4:
                    _x = 7;
                    break;
            }
        }

        return new Position(_x, _y);
    }
}