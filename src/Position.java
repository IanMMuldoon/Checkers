public class Position {
    boolean _isValid;
    int _x;
    int _y;
    int _row;
    int _position;


    public Position(int x, int y){
        _x = x;
        _y = y;

    }
    public Position(int row, int position, int var){
        _row = row;
        _position = position;
        _isValid = true;

    }
    public Position(boolean Valid){
        _isValid = Valid;
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
    public static Position getPieceRP(int x, int y, int var) { //RP stand for row position
        if((x + y) % 2 == 0){
            return new Position(false);
        }
        int _position = 0;
        int _row = Math.abs((y) - 8);
        if (y % 2 == 1) {
            switch (x) {
                case 0:
                    _position = 1;
                    break;
                case 2:
                    _position = 2;
                    break;
                case 4:
                    _position = 3;
                    break;
                case 6:
                    _position = 4;
                    break;
            }
        } else {
            switch (x) {
                case 1:
                    _position = 1;
                    break;
                case 3:
                    _position = 2;
                    break;
                case 5:
                    _position = 3;
                    break;
                case 7:
                    _position = 4;
                    break;
            }
        }
        return new Position(_row, _position, var);
    }



}
