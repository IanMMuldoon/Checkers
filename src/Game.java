public class Game
{
    public Piece[] _pieces;   //array of Piece class
    private int _gameID;
    private int _player1UserID;
    private int _player2UserID;
    private int _totalMoves;
    private int _winnerUserID;

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
    public void _reset()
    {
        this._totalMoves = 0;
        this._winnerUserID = 0;

        //Set up the pieces (maybe this can be two arrays? player 1 pieces/player 2 pieces?
        this._pieces = new Piece[24];

        //We will define ROWS as 1-8
        //And POSITIONS on those rows as 1-4

        //We will define PLAYER 1 start position as the BOTTOM (rows 1-3)
        for (int i = 1; i <= 12; i++)
        {
            this._pieces[i] = new Piece();

            this._pieces[i]._playerID = this._player1UserID;
            //integer division for the row
            this._pieces[i]._row = (i + 3) / 4;
            this._pieces[i]._position = (i - (this._pieces[i]._row-1)*4);

            this._pieces[i] = Main.makePieceConvert(PieceType.White, _pieces[i]._row, _pieces[i]._position);
        }

        //We will define PLAYER 2 start position as the TOP (rows 6-8)
        for (int i = 13; i < 24; i++)
        {
            this._pieces[i] = new Piece();

            this._pieces[i]._playerID = this._player2UserID;
            //integer division for the row
            this._pieces[i]._row = ((i + 3) / 4) + 2; //skipping two rows
            this._pieces[i]._position = (i-12) - (this._pieces[i]._row-6)*4;

            this._pieces[i] = Main.makePieceConvert(PieceType.RED, _pieces[i]._row, _pieces[i]._position);
//How the position formula works
//            @i = 1: 1 - (1-1)*4 = 1
//            @i = 11: 11 - (3-1)*4 = 11-8 = 3
//            @i = 13: (13-12) - (((13 + 3) / 4) + 2 -6)*4 -> 1 - (4+2-6)*4 -> 1
//            @i = 20: (20-12) - (((20 + 3) / 4) + 2 -6)*4 -> 8 - (5+2-6)*4 -> 8 - 4 = 4
//            @i = 17: (17-12) - (((17 + 3) / 4) + 2 -6)*4 -> 5 - (5+2-6)*4 -> 5 - 4 = 1
//            @i = 23: (23-12) - (((23 + 3) / 4) + 2 -6)*4 -> 11 - (6+2-6)*4 -> 11 - 8 = 3
        }
    }



    public boolean _canMovePiece(Piece piece, int to_row, int to_position)
    {
        //Determine if this is a valid position
        boolean can_move = false;

        //First check if there is a piece there
        if (_squareHasPiece(to_row, to_position))
            return false;

        if (piece._isKing)
        {

        }
        else
        {
            //regular pieces
            if (this._pieceIsPlayer1(piece))
            {
                //player 1 regular pieces move UP

                //check if moving or jumping
                if (to_row == (piece._row + 1))
                {
                    //Moving
                    if (piece._row %2 == 0 //Even rows move LEFT 0 or RIGHT 1
                            && (to_position == piece._position || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
                else if (to_row == (piece._row + 2))
                {
                    //Jumping
                    if (piece._row %2 == 0 //Even rows move LEFT 1 or RIGHT 1
                            && (to_position == (piece._position - 1) || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
//                else
//                    can_move = false;
            }
            else
            {
                //player 2 regular pieces move DOWN

                //check if moving or jumping
                if (to_row == (piece._row - 1))
                {
                    //Moving
                    if (piece._row %2 == 0 //Even rows move LEFT 0 or RIGHT 1
                            && (to_position == piece._position || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
                else if (to_row == (piece._row - 2))
                {
                    //Jumping
                    if (piece._row %2 == 0 //Even rows move LEFT 1 or RIGHT 1
                            && (to_position == (piece._position - 1) || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
//                else
//                    can_move = false;
            }
        }

        return can_move;
    }

    private boolean _squareHasPiece(int row, int position)
    {
        boolean has_piece = false;

        for (Piece piece: this._pieces)
        {
            if (piece._row == row && piece._position == position)
            {
                has_piece = true;
                break;
            }
        }

        return has_piece;
    }

    public boolean _movePiece(Piece piece, int to_row, int to_position)
    {
        if (this._canMovePiece(piece, to_row, to_position))
        {
            piece._row = to_row;
            piece._position = to_position;
        }

        //TODO: logic for if a piece was captured

        //TODO: return true if must jump again? (or maybe return an enum?)

        return true;
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
}