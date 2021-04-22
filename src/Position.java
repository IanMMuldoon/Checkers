public class Position {
    int _x;
    int _y;
    int row;
    int position;

    public Position(int x, int y){
        _x = x;
        _y = y;
    }

    public static Position getPieceXY(Piece piece) {
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
    public static Position getPieceRP(int x, int y) { //RP stand for row position
        int row = 0;
        int position = Math.abs((y) - 8);
        if (y % 2 == 1) {
            switch (x) {
                case 0:
                    position = 1;
                    break;
                case 2:
                    position = 2;
                    break;
                case 4:
                    position = 3;
                    break;
                case 6:
                    position = 4;
                    break;
            }
        } else {
            switch (x) {
                case 1:
                    position = 1;
                    break;
                case 3:
                    position = 2;
                    break;
                case 5:
                    position = 3;
                    break;
                case 7:
                    position = 4;
                    break;
            }
        }

        return new Position(position, row);
    }



}