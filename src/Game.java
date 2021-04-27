public class Game
{
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public Tile[][] board = new Tile[WIDTH][HEIGHT];
    public Piece[] _pieces;   //array of Piece class
    private int _gameID;
    private int _player1UserID;
    private int _player2UserID;
    private int _totalMoves;
    private int _winnerUserID;
    private int _currentPlayerID;

    public Game(int player1userid, int player2userid)
    {
        this._player1UserID = player1userid;
        this._player2UserID = player2userid;

        this._reset();
    }

    public int _getGameID() { return this._gameID; }
    public int _getPlayer1UserID() { return this._player1UserID; }
    public int _getPlayer2UserID() { return this._player2UserID; }
    public int _getTotalMoves() { return this._totalMoves; }
    public int _getWinnerUserID() { return this._winnerUserID; }
    public int _getCurrentPlayerID() { return this._currentPlayerID; }

    public void _reset()
    {
        this._totalMoves = 0;
        this._winnerUserID = 0;
        this._currentPlayerID = this._player1UserID;

        //Set up the pieces (maybe this can be two arrays? player 1 pieces/player 2 pieces?
        this._pieces = new Piece[25];

        //We will define ROWS as 1-8
        //And POSITIONS on those rows as 1-4

        //We will define PLAYER 1 start position as the BOTTOM (rows 1-3)
        for (int i = 1; i <= 12; i++)
        {

            this._pieces[i] = new Piece();  //makePiece();
            this._pieces[i].type = PieceType.RED;
            this._pieces[i]._playerID = this._player1UserID;
            //integer division for the row
            this._pieces[i]._row = (i + 3) / 4;
            this._pieces[i]._position = (i - (this._pieces[i]._row-1)*4);

        }

        //We will define PLAYER 2 start position as the TOP (rows 6-8)
        for (int i = 13; i <= 24; i++)
        {
            this._pieces[i] = new Piece(); //makePiece();
            this._pieces[i].type = PieceType.White;
            this._pieces[i]._playerID = this._player2UserID;
            //integer division for the row
            this._pieces[i]._row = ((i + 3) / 4) + 2; //skipping two rows
            this._pieces[i]._position = (i-12) - (this._pieces[i]._row-6)*4;


//How the position formula works
//            @i = 1: 1 - (1-1)*4 = 1
//            @i = 11: 11 - (3-1)*4 = 11-8 = 3
//            @i = 13: (13-12) - (((13 + 3) / 4) + 2 -6)*4 -> 1 - (4+2-6)*4 -> 1
//            @i = 20: (20-12) - (((20 + 3) / 4) + 2 -6)*4 -> 8 - (5+2-6)*4 -> 8 - 4 = 4
//            @i = 17: (17-12) - (((17 + 3) / 4) + 2 -6)*4 -> 5 - (5+2-6)*4 -> 5 - 4 = 1
//            @i = 23: (23-12) - (((23 + 3) / 4) + 2 -6)*4 -> 11 - (6+2-6)*4 -> 11 - 8 = 3
        }
    }

    private Piece _pieceToCapture = null;

    public boolean _canMovePiece(Piece piece, int to_row, int to_position)
    {
        //Determine if this is a valid position
        boolean can_move = false;
        this._pieceToCapture = null;

        //First check if there is a piece there
        if (_squareHasPiece(to_row, to_position))
            return false;

        if (piece._isKing)
        {
            //King pieces
        }
        else
        {
            //regular pieces

            //player 1 regular pieces move UP = 1, Player 2 regular pieces move DOWN = -1
            int directionmodifier = 1;
            if (!this._pieceIsPlayer1(piece))
                directionmodifier = -1;

            //check if moving or jumping
            if (to_row == (piece._row + directionmodifier))
            {
                //Moving
                if (piece._row %2 == 0 //Even rows move LEFT 0 or RIGHT 1
                        && (to_position == piece._position || to_position == (piece._position + 1 )))
                {
                    can_move = true;
                }
                else if (piece._row %2 == 1 //Odd rows move LEFT 1 or RIGHT 0
                        && (to_position == (piece._position - 1) || to_position == piece._position))
                {
                    can_move = true;
                }
            }
            else if (to_row == (piece._row + 2 * directionmodifier))
            {
                //Jumping
                //Involves two rows, and above Moving logic * 2 results in same logic for Odd and even rows
                if (to_position == (piece._position - 1) || to_position == (piece._position + 1 ))
                {
                    //jumped position depends on row chirality
                    int jumpedposition = 0;
                    if (piece._row %2 == 0 && to_position < piece._position) //Jumping LEFT from EVEN row
                        jumpedposition = piece._position;
                    else if (piece._row %2 == 1 && to_position < piece._position) //Jumping LEFT from ODD row
                        jumpedposition = piece._position - 1;
                    else if (piece._row %2 == 0 && to_position > piece._position) //Jumping RIGHT from EVEN row
                        jumpedposition = piece._position + 1;
                    else if (piece._row %2 == 1 && to_position > piece._position) //Jumping RIGHT from ODD row
                        jumpedposition = piece._position;

                    //Check that the space inbetween from and to has an opponent piece
                    Piece jumpedpiece = this._getSquarePiece(piece._row + directionmodifier, jumpedposition);
                    if  (jumpedpiece != null && jumpedpiece._playerID != piece._playerID)
                    {
                        can_move = true;
                        this._pieceToCapture = jumpedpiece;
                    }
                }
            }
        }

        return can_move;
    }

    private Piece _getSquarePiece(int row, int position)
    {
        Piece foundpiece = null;

        for (Piece piece: this._pieces)
        {
            if(piece != null) {
                if (piece._row == row && piece._position == position) {
                    foundpiece = piece;
                    break;
                }
            }
        }

        return foundpiece;
    }

    private boolean _squareHasPiece(int row, int position)
    {
        return (this._getSquarePiece(row, position) != null);
    }

    private boolean _mustJumpAgain = false;
    public boolean getMustJumpAgain() { return this._mustJumpAgain; }

    public boolean _movePiece(Piece piece, int to_row, int to_position)
    {
        boolean moved = false;
        this._mustJumpAgain = false;

        if (this._canMovePiece(piece, to_row, to_position))
        {
            piece._row = to_row;
            piece._position = to_position;

            Position convert = Position.getPieceXY(piece);
            piece.move(convert._x, convert._y);

            //Check if a piece was captured
            if (this._pieceToCapture != null) {
                //Remove the captured piece
                Piece[] newlist = new Piece[this._pieces.length - 1];
                int i = 0;
                for (Piece piecetocopy : this._pieces) {
                    if (piecetocopy != this._pieceToCapture)
                        newlist[i++] = piecetocopy;
                    else
                        piecetocopy = null;
                }

                this._pieces = newlist;
                this._pieceToCapture = null;

                //player 1 regular pieces move UP = 1, Player 2 regular pieces move DOWN = -1
                int directionmodifier = 1;
                if (!this._pieceIsPlayer1(piece))
                    directionmodifier = -1;

                //return true if must jump again
                //check if the piece is allowed to make another jump left or right
                this._mustJumpAgain = (this._canMovePiece(piece, piece._row + 2 * directionmodifier, piece._position - 1)
                        || this._canMovePiece(piece, piece._row + 2 * directionmodifier, piece._position + 1));
            }

            if (!this._mustJumpAgain)
                this._toggleCurrentPlayer();

            moved = true;
        }

        return moved;
    }

    private void _toggleCurrentPlayer()
    {
        if (this._currentPlayerID == this._player1UserID)
            this._currentPlayerID = this._player2UserID;
        else
            this._currentPlayerID = this._player1UserID;
    }

    public void _kingPiece(Piece piece)
    {
        if (piece._isKing)
            return;

        //Determine if this piece is actually in appropriate row
        if ((this._pieceIsPlayer1(piece) && piece._row == 8)
                || (piece._row == 1))
            piece._isKing = true;
    }

    private boolean _pieceIsPlayer1(Piece piece)
    {
        if (piece._playerID == this._player1UserID)
            return true;
        else
            return false;
    }
    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
}
